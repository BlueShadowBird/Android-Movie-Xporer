package id.web.dedekurniawan.moviexplorer.movie.domain.usecase

import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase: ModuleElement.ModuleUseCase<Movie>{
    fun saveMovieToFavorite(movie: Movie)
    fun deleteFavoriteMovie(movieId: String)
    fun getAllFavorite(): Flow<List<Movie>>
    fun isFavorite(movieId: String): Flow<Boolean>
}