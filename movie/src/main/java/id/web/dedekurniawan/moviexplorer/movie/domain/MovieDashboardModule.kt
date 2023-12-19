package id.web.dedekurniawan.moviexplorer.movie.domain

import android.content.Context
import android.content.Intent
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.movie.R
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.presentation.view.MovieDetailActivity
import id.web.dedekurniawan.moviexplorer.movie.presentation.view.MovieListFragment

class MovieModuleElement(moduleUseCase: ModuleUseCase<Movie>) : ModuleElement(moduleUseCase) {
    override val name = "movie"
    override val itemMenuId = R.drawable.movie_icon

    override fun createFragment() = MovieListFragment()
    override fun startActivity(context: Context, itemId: Int) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, itemId)
        context.startActivity(intent)
    }
}