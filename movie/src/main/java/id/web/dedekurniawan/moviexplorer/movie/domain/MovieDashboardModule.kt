package id.web.dedekurniawan.moviexplorer.movie.domain

import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.movie.R
import id.web.dedekurniawan.moviexplorer.movie.presentation.view.MovieListFragment

class MovieDashboardModule: ModuleElement.DashboardModule() {
    override val itemMenuId = R.drawable.movie_icon

    override fun createFragment() = MovieListFragment()
}