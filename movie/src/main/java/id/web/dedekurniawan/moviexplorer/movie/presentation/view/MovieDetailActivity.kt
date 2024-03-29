package id.web.dedekurniawan.moviexplorer.movie.presentation.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import id.web.dedekurniawan.moviexplorer.movie.R
import id.web.dedekurniawan.moviexplorer.movie.presentation.viewmodel.MovieViewModel
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.utils.alert
import org.koin.androidx.viewmodel.ext.android.viewModel
import id.web.dedekurniawan.moviexplorer.core.utils.loadGLide
import id.web.dedekurniawan.moviexplorer.core.utils.numberFormat
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import java.util.*
import kotlin.math.roundToInt
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.movie.databinding.ActivityMovieBinding

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)

        installSplashScreen()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        viewModel.result.observe(this){ result ->
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


            if(movie.genreList?.isNotEmpty() == true){
                movieGenre.setList(movie.genreList)
            }else{
                movieGenre.visibility = View.GONE
            }

            movieDuration.text = if ((movie.runtime ?: 0) > 0){
                val hour = movie.runtime!!.div(60)
                val minute = movie.runtime %60

                "${if (hour > 0)resources.getQuantityString(R.plurals.duration_hour, hour, hour) else ""} ${if (minute > 0)resources.getQuantityString(R.plurals.duration_minute, minute, minute) else ""}"
            }else{
                duration.visibility = View.GONE
                movieDuration.visibility = View.GONE
                null
            }

            movieOriginalLanguage.text = movie.originalLanguage?.let { Locale.forLanguageTag(it).displayLanguage }
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

            loadGLide(movieImage, movie.posterPath)
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