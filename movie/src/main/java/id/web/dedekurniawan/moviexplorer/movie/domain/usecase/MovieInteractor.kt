package id.web.dedekurniawan.moviexplorer.movie.domain.usecase

import com.google.gson.Gson
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.Favorite
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IFavoriteRepository
import id.web.dedekurniawan.moviexplorer.core.domain.repository.ISettingRepository
import id.web.dedekurniawan.moviexplorer.core.domain.usecase.BaseFavoriteInteractor
import id.web.dedekurniawan.moviexplorer.movie.common.MODULE_NAME
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(
    private val movieRepository: IMovieRepository,
    private val settingRepository: ISettingRepository,
    favoriteRepository: IFavoriteRepository
): MovieUseCase, BaseFavoriteInteractor<Movie>(favoriteRepository) {
    override suspend fun retrieveDetail(itemId: Int) = movieRepository.retrieveMovie(itemId)

    override suspend fun retrieveMovieImages(movieId: Int) = movieRepository.retrieveMovieImages(movieId)

    override suspend fun retrieveMovieVideos(movieId: Int) = movieRepository.retrieveMovieVideos(movieId)

    override suspend fun quickSearch(query: String) = movieRepository.quickSearchMovie(query) as Flow<Result<List<QuickSearchBaseModel>>>

    override suspend fun retrieveTrending() = movieRepository.retrieveTrendingMovie()

    override suspend fun search(query: String) = movieRepository.searchMovie(query, settingRepository.getIncludeAdultSetting())

    override val moduleName = MODULE_NAME

    override fun convertFromJson(favorite: Favorite): Movie = gson.fromJson(favorite.data, Movie::class.java)

    override fun createFavoriteObject(data: Movie) = Favorite(
        MODULE_NAME,
        data.id,
        data.title,
        Gson().toJson(Movie(
            data.id,
            data.title,
            releaseDate = data.releaseDate,
            posterPath = data.posterPath.toString(),
            overview = data.overview,
            voteCount = data.voteCount,
            voteAverage = data.voteAverage
        ))
    )
}