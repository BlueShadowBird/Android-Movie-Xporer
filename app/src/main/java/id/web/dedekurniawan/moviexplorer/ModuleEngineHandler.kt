package id.web.dedekurniawan.moviexplorer

import android.content.Context
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.movie.domain.MovieModuleElement
import id.web.dedekurniawan.moviexplorer.movie.domain.usecase.MovieUseCase


class ModuleEngineHandler(
    context: Context,
    val moduleEngine: ModuleEngine
) {
    fun initMovieModule(useCase: MovieUseCase){
        val moduleElement = MovieModuleElement(
            useCase
        )
        moduleEngine.addModuleElement(moduleElement)
    }
}