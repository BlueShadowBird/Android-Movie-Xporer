package id.web.dedekurniawan.moviexplorer.presentation.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.web.dedekurniawan.moviexplorer.R
import id.web.dedekurniawan.moviexplorer.databinding.ActivityMovieBinding
import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.utils.alert
import org.koin.androidx.viewmodel.ext.android.viewModel
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.presentation.viewmodel.MovieViewModel
import id.web.dedekurniawan.moviexplorer.core.utils.loadGLide
import id.web.dedekurniawan.moviexplorer.core.utils.numberFormat
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import java.util.*
import kotlin.math.roundToInt

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.movie.observe(this){ result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    result.data?.let { bindMovieData(it) }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    alert(binding.root, result.message.toString())
                }
            }
        }
        viewModel.retrieveMovie(intent.getIntExtra(EXTRA_MOVIE_ID, 0))
    }

    @SuppressLint("SetTextI18n")
    private fun bindMovieData(movie: Movie) {
        supportActionBar?.title = movie.title
        binding.run {
            movieTagline.text = movie.tagline

            if ((movie.voteCount ?: 0) > 0) {
                val score = (movie.voteAverage?:0F).times(10).roundToInt()
                movieVoteAverage.setProgress(score)
                movieVoteAverage.setProgressColor(reviewScoreToColor(score))

                userScore.text = getString(R.string.user_score)
            }else{
                movieVoteAverage.setProgress(-1)
                userScore.text = getString(R.string.not_scored)
            }

            movieOverview.text = "\t${movie.overview.replace("\n", "\n\t")}"    // tab to each new line
            movie.releaseDate?.let {
                movieReleaseDate.text = movie.releaseDate.toString()
            }

            movieGenreList.text = if(movie.genreList?.isNotEmpty() == true){
                val genres = movie.genreList!!
                val sb = StringBuffer(genres[0])
                genres.slice(1 until genres.size).forEach {
                    sb.append(" | ").append(it)
                }
                sb.toString()
            }else{
                movieGenreList.visibility = View.GONE
                null
            }
            movieDuration.text = if ((movie.runtime ?: 0) > 0){
                val hour = movie.runtime!!.div(60)
                val minute = movie.runtime!!%60

                "${if (hour > 0)resources.getQuantityString(R.plurals.duration_hour, hour) else ""} ${if (minute > 0)resources.getQuantityString(R.plurals.duration_minute, minute) else ""}"
            }else{
                movieDuration.visibility = View.GONE
                null
            }

            movieOriginalLanguage.text = movie.original_language?.let { Locale.forLanguageTag(it).displayLanguage }
            movieStatus.text = movie.status

            if((movie.budget ?: 0) > 0){
                movieBudget.text = getString(R.string.dolar_amount_format, numberFormat(movie.budget?.toFloat()))
            }else{
                budget.visibility = View.GONE
                movieBudget.visibility = View.GONE
            }
            if((movie.revenue ?: 0) > 0){
                movieRevenue.text = getString(R.string.dolar_amount_format, numberFormat(movie.revenue?.toFloat()))
            }else{
                revenue.visibility = View.GONE
                movieRevenue.visibility = View.GONE
            }

            loadGLide(movieImage, movie)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object{
        const val EXTRA_MOVIE_ID = "extraMovieId"
    }
}