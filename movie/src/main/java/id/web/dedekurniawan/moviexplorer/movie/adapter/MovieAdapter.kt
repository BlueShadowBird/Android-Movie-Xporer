package id.web.dedekurniawan.moviexplorer.movie.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.web.dedekurniawan.moviexplorer.core.R
import id.web.dedekurniawan.moviexplorer.core.utils.loadImage
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import id.web.dedekurniawan.moviexplorer.movie.databinding.RvMovieBinding
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.presentation.view.MovieDetailActivity
import kotlin.math.roundToInt


class MovieAdapter(private val activity: Activity): ListAdapter<Movie, MovieAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
){

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(activity, parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class ViewHolder private constructor(private val activity: Activity, private val binding: RvMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie){
            val context = itemView.context

            binding.run {
                movieTitle.text = movie.title

                if (movie.voteCount !=null) {
                    if (movie.voteCount > 0) {
                        val score = (movie.voteAverage?:0F).times(10).roundToInt()
                        movieVoteAverage.setProgress(score)
                        movieVoteAverage.setProgressColor(reviewScoreToColor(score))

                        voteAverageText.text = context.getString(R.string.user_score)
                    }else{
                        movieVoteAverage.setProgress(-1)

                        voteAverageText.text = context.getString(R.string.not_scored)
                    }
                    movieVoteAverage.visibility = View.VISIBLE
                    voteAverageText.visibility = View.VISIBLE
                }else{
                    movieVoteAverage.visibility = View.GONE
                    voteAverageText.visibility = View.GONE
                }

                movieOverview.text = movie.overview
                movie.releaseDate?.let {
                    movieYear.text = context.getString(R.string.movie_year_value, it.year.toString())
                }

                loadImage(context, movieImage, movie.posterPath)

                itemView.setOnClickListener {
                    startDetailActivity(movie, binding)
                }
            }
        }

        private fun startDetailActivity(movie: Movie, binding: RvMovieBinding){
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_IMAGE, movie.posterPath)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                binding.movieImage,
                ViewCompat.getTransitionName(binding.movieImage)!!
            )

            activity.startActivity(intent, options.toBundle())
        }

        companion object{
            fun from(activity: Activity, parent: ViewGroup) = ViewHolder(activity, RvMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}