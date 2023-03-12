package id.web.dedekurniawan.moviexplorer.core.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import id.web.dedekurniawan.moviexplorer.core.R
import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import kotlin.math.min

fun loadGLide(movieImage: ImageView, movie: Movie){
    Glide.with(movieImage)
        .load(if(movie.posterPath.isNullOrEmpty()){
            R.drawable.ic_default_image
        }else{
            "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        })
        .into(movieImage)
}

fun reviewScoreToColor(score: Int) = Color.argb(
    255,
    min(255F-(score-50)*5.1F, 255F).toInt(),
    min(score*5.1F, 255F).toInt(),
    0)

fun alert(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}