package id.web.dedekurniawan.moviexplorer.core.utils

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import id.web.dedekurniawan.moviexplorer.core.R
import kotlin.math.min

fun loadGLide(movieImage: ImageView, moviePoster: String?){
    Glide.with(movieImage)
        .load(
            if(moviePoster.isNullOrEmpty()){
                R.drawable.ic_default_image
            }else{
                "https://image.tmdb.org/t/p/w500${moviePoster}"
            }
        )
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

fun alert(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun changeTheme(theme: Int?){
    AppCompatDelegate.setDefaultNightMode(theme ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}

fun alert(view: View, tag: String?, message: String){
    alert(view, message)
    Log.i(tag, message)
}

fun alert(context: Context, tag: String?, message: String){
    alert(context, message)
    Log.i(tag, message)
}