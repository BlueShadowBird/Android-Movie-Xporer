package id.web.dedekurniawan.moviexplorer.core.domain.repository

import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result

interface IMovieRepository {
    suspend fun searchMovie(query: String, includeAdult: Boolean = false): Flow<Result<List<Movie>>>
    suspend fun retrieveMovie(movieId: Int): Flow<Result<Movie>>
    fun saveGameToFavorite(movie: Movie)
    fun deleteFavoriteGame(movieId: String)
    fun getAllFavorite(): Flow<List<Movie>>
    fun isFavorite(movieId: String): Flow<Boolean>
}