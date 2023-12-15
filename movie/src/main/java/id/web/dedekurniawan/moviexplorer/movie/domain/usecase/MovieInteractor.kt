package id.web.dedekurniawan.moviexplorer.movie.domain.usecase

import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.core.domain.repository.ISettingRepository
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository, private val settingRepository: ISettingRepository):
    MovieUseCase {
    override suspend fun retrieveDetail(movieId: Int) = movieRepository.retrieveMovie(movieId)

    override fun saveMovieToFavorite(movie: Movie) {
        movieRepository.saveMovieToFavorite(movie)
    }

    override fun deleteFavoriteMovie(movieId: String) {
        movieRepository.deleteFavoriteMovie(movieId)
    }

    override fun getAllFavorite() = movieRepository.getAllFavorite()

    override fun isFavorite(movieId: String) = movieRepository.isFavorite(movieId)
    override suspend fun quickSearch(query: String) = movieRepository.quickSearchMovie(query) as Flow<Result<List<QuickSearchBaseModel>>>

    override suspend fun retrieveTrending() = movieRepository.retrieveTrendingMovie()

    override suspend fun search(query: String) = movieRepository.searchMovie(query, settingRepository.getIncludeAdultSetting())
}