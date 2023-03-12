package id.web.dedekurniawan.moviexplorer.core.domain.usecase

import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IMovieRepository

class MovieInteractor(private val repository: IMovieRepository): MovieUseCase {
    override suspend fun searchMovie(query: String) = repository.searchMovie(query)

    override suspend fun retrieveMovie(movieId: Int) = repository.retrieveMovie(movieId)

    override fun saveGameToFavorite(movie: Movie) {
        repository.saveGameToFavorite(movie)
    }

    override fun deleteFavoriteGame(movieId: String) {
        repository.deleteFavoriteGame(movieId)
    }

    override fun getAllFavorite() = repository.getAllFavorite()

    override fun isFavorite(movieId: String) = repository.isFavorite(movieId)
}