package id.web.dedekurniawan.moviexplorer.core.domain.usecase

import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IMovieRepository
import id.web.dedekurniawan.moviexplorer.core.domain.repository.ISettingRepository

class MovieInteractor(private val movieRepository: IMovieRepository, private val settingRepository: ISettingRepository): MovieUseCase {
    override suspend fun searchMovie(query: String) = movieRepository.searchMovie(query, settingRepository.getIncludeAdultSetting())

    override suspend fun retrieveMovie(movieId: Int) = movieRepository.retrieveMovie(movieId)

    override fun saveGameToFavorite(movie: Movie) {
        movieRepository.saveGameToFavorite(movie)
    }

    override fun deleteFavoriteGame(movieId: String) {
        movieRepository.deleteFavoriteGame(movieId)
    }

    override fun getAllFavorite() = movieRepository.getAllFavorite()

    override fun isFavorite(movieId: String) = movieRepository.isFavorite(movieId)
}