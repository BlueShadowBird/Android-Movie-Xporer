package id.web.dedekurniawan.moviexplorer.movie.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.ads.AdRequest
import id.web.dedekurniawan.moviexplorer.core.presentation.view.YoutubeActivity
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.MediaSourceInfo
import id.web.dedekurniawan.moviexplorer.core.presentation.view.CarouselMediaActivity
import id.web.dedekurniawan.moviexplorer.core.utils.alert
import id.web.dedekurniawan.moviexplorer.core.utils.getFavoriteDrawable
import id.web.dedekurniawan.moviexplorer.core.utils.loadImage
import id.web.dedekurniawan.moviexplorer.core.utils.numberFormat
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import id.web.dedekurniawan.moviexplorer.movie.R
import id.web.dedekurniawan.moviexplorer.movie.databinding.ActivityMovieBinding
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.presentation.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class MovieDetailActivity : AppCompatActivity() {
    private var movieId by Delegates.notNull<Int>()
    private lateinit var binding: ActivityMovieBinding
    private val viewModel: MovieViewModel by viewModel()
    private var notInproccess = false
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)

        installSplashScreen()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        intent.getStringExtra(EXTRA_MOVIE_IMAGE)?.let {
            loadImage(this, binding.movieImage, it)
        }

        viewModel.run {
            result.observe(this@MovieDetailActivity){ result ->
                when(result){
                    is Result.Loading -> {
                        notInproccess = false       // processing
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        result.data?.let {
                            movie = it
                            listenFavorite(movie.id)
                            bindMovieData()
                        }
                        notInproccess = true        // processing done
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        alert(binding.root, result.message.toString())
                        notInproccess = true        // processing done
                    }
                }
            }

            mediaResult.observe(this@MovieDetailActivity){ result ->
                when(result){
                    is Result.Loading -> {
                        notInproccess = false       // processing
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        result.data?.let {
                            val intent = Intent(this@MovieDetailActivity, CarouselMediaActivity::class.java)
                            intent.putParcelableArrayListExtra(CarouselMediaActivity.MEDIA_LIST_EXTRA, ArrayList(it))
                            startActivity(intent)
                        }
                        notInproccess = true        // processing done
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        alert(binding.root, result.message.toString())
                        notInproccess = true        // processing done
                    }
                }
            }

            youtubeResult.observe(this@MovieDetailActivity){ result ->
                when(result){
                    is Result.Loading -> {
                        notInproccess = false       // processing
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        result.data?.let {  data ->
                            val intent = Intent(this@MovieDetailActivity, YoutubeActivity::class.java)
                            intent.putStringArrayListExtra(
                                YoutubeActivity.EXTRA_ID_LIST, ArrayList(
                                data.filter { mediaSourceInfo ->
                                    MediaSourceInfo.MEDIA_SOURCE_YOUTUBE == mediaSourceInfo.source
                                }.map { mediaSourceInfo ->
                                    mediaSourceInfo.data
                                }
                            ))
                            startActivity(intent)
                        }
                        notInproccess = true        // processing done
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        alert(binding.root, result.message.toString())
                        notInproccess = true        // processing done
                    }
                }
            }

            isFavoriteResult.observe(this@MovieDetailActivity){
                binding.favorite.setImageResource(getFavoriteDrawable(it))
                movie.isFavorite = it
            }
        }

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)

        viewModel.retrieveMovie(movieId)
    }

    @SuppressLint("SetTextI18n")
    private fun bindMovieData() {
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
                val minute = movie.runtime!! %60

                "${if (hour > 0)resources.getQuantityString(R.plurals.duration_hour, hour, hour) else ""} ${if (minute > 0)resources.getQuantityString(R.plurals.duration_minute, minute, minute) else ""}"
            }else{
                duration.visibility = View.GONE
                movieDuration.visibility = View.GONE
                null
            }

            movieOriginalLanguage.text = movie.originalLanguage?.let { Locale.forLanguageTag(it).displayLanguage }
            movieStatus.text = movie.status

            if((movie.budget ?: 0) > 0){
                movieBudget.text = getString(R.string.dollar_amount_format, numberFormat(movie.budget?.toFloat()))
            }else{
                budget.visibility = View.GONE
                movieBudget.visibility = View.GONE
            }
            if((movie.revenue ?: 0) > 0){
                movieRevenue.text = getString(R.string.dollar_amount_format, numberFormat(movie.revenue?.toFloat()))
            }else{
                revenue.visibility = View.GONE
                movieRevenue.visibility = View.GONE
            }

            loadImage(this@MovieDetailActivity, movieImage, movie.posterPath)


            movieImage.setOnClickListener {
                if(notInproccess)
                    viewModel.retrieveMovieImages(movieId)
            }

            play.setOnClickListener {
                if(notInproccess)
                    viewModel.retrieveMovieVideos(movieId)
            }

            favorite.setOnClickListener {
                if(movie.isFavorite){
                    viewModel.deleteFavorite(movie)
                }else{
                    viewModel.saveToFavorite(movie)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.GONE  // to rehide hide progressBar after back from CarouselMediaActivity
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object{
        const val EXTRA_MOVIE_ID = "extraMovieId"
        const val EXTRA_MOVIE_IMAGE = "extraMovieImage"
    }
}