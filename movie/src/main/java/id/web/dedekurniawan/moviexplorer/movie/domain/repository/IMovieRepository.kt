package id.web.dedekurniawan.moviexplorer.movie.domain.repository

import kotlinx.coroutines.flow.Flow
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.MediaSourceInfo
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.domain.model.MovieQuickSearchModel

interface IMovieRepository {
    suspend fun retrieveTrendingMovie(): Flow<Result<List<Movie>>>
    suspend fun quickSearchMovie(query: String): Flow<Result<List<MovieQuickSearchModel>>>
    suspend fun searchMovie(query: String, includeAdult: Boolean = false): Flow<Result<List<Movie>>>
    suspend fun retrieveMovie(movieId: Int): Flow<Result<Movie>>
    suspend fun retrieveMovieImages(movieId: Int): Flow<Result<List<MediaSourceInfo>>>
    suspend fun retrieveMovieVideos(movieId: Int): Flow<Result<List<MediaSourceInfo>>>
}