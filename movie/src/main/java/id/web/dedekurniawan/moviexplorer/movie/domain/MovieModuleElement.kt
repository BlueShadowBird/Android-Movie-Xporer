package id.web.dedekurniawan.moviexplorer.movie.domain

import android.content.Context
import android.content.Intent
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.movie.R
import id.web.dedekurniawan.moviexplorer.movie.domain.usecase.MovieUseCase
import id.web.dedekurniawan.moviexplorer.movie.presentation.view.MovieDetailActivity
import id.web.dedekurniawan.moviexplorer.movie.presentation.view.MovieListFragment
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MovieModuleElement(moduleUseCase: MovieUseCase) : ModuleElement(moduleUseCase) {
    override val name = "movie"
    override val itemMenuId = R.drawable.movie_icon

    override fun createFragment() = MovieListFragment()
    override fun startActivity(context: Context, itemId: Int) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, itemId)
        context.startActivity(intent)
    }
}

class MovieModuleElementProvider: ModuleEngine.ModuleElementProvider, KoinComponent {
    override fun getModuleElement() = get<MovieModuleElement>()
}