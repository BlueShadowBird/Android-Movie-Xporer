package id.web.dedekurniawan.moviexplorer.app

import android.content.Context
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.movie.domain.MovieDashboardModule
import id.web.dedekurniawan.moviexplorer.movie.domain.usecase.MovieUseCase


class ModuleEngineHandler(
    context: Context,
    val moduleEngine: ModuleEngine
) {
    fun initMovieModule(dashboardModule: MovieDashboardModule, viewModel: MovieUseCase){
        val moduleElement = ModuleElement(
            "Movie",
            dashboardModule,
            viewModel as ModuleElement.ModuleUseCase<Any>
        )
        moduleEngine.addModuleElement(moduleElement)
    }
}