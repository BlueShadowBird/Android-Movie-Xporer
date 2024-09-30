package id.web.dedekurniawan.moviexplorer.movie.domain.usecase

import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.core.domain.model.MediaSourceInfo
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase: ModuleElement.ModuleUseCase<Movie>{
    suspend fun retrieveMovieImages(movieId: Int): Flow<Result<List<MediaSourceInfo>>>
    suspend fun retrieveMovieVideos(movieId: Int): Flow<Result<List<MediaSourceInfo>>>
}