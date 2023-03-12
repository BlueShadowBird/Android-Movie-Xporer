package id.web.dedekurniawan.moviexplorer.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.web.dedekurniawan.moviexplorer.core.R
import id.web.dedekurniawan.moviexplorer.core.databinding.RvMovieBinding
import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.utils.loadGLide
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import kotlin.math.roundToInt

class MovieAdapter: ListAdapter<Movie, MovieAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
){
    var onClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, onClickListener)
    }

    class ViewHolder private constructor(private val binding: RvMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onClickListener: ((Movie) -> Unit)?){
            val context = itemView.context

            binding.run {
                movieTitle.text = movie.title

                if ((movie.voteCount ?: 0) > 0) {
                    val score = (movie.voteAverage?:0F).times(10).roundToInt()
                    movieVoteAverage.setProgress(score)
                    movieVoteAverage.setProgressColor(reviewScoreToColor(score))

                    voteAverageText.text = context.getString(R.string.user_score)
                }else{
                    movieVoteAverage.setProgress(-1)

                    voteAverageText.text = context.getString(R.string.not_scored)
                }

                movieOverview.text = movie.overview
                movie.releaseDate?.let {
                    movieYear.text = context.getString(R.string.movie_year_value, it.year.toString())
                }
                loadGLide(movieImage, movie)

                itemView.setOnClickListener {
                    onClickListener?.invoke(movie)
                }
            }
        }

        companion object{
            fun from(parent: ViewGroup) = ViewHolder(RvMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}