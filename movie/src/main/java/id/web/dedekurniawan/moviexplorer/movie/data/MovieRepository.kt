package id.web.dedekurniawan.moviexplorer.movie.data

import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result.Loading
import id.web.dedekurniawan.moviexplorer.core.domain.model.MediaSourceInfo
import id.web.dedekurniawan.moviexplorer.movie.data.remote.MovieApiService
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.domain.repository.IMovieRepository
import id.web.dedekurniawan.moviexplorer.movie.utils.toDomainModel
import id.web.dedekurniawan.moviexplorer.movie.utils.toMediaSourceInfo
import id.web.dedekurniawan.moviexplorer.movie.utils.toQuickSearchModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val movieApiService: MovieApiService,
//    private val favoriteDao: FavoriteDao
//    private val localDataSource: LocalDataSource
): IMovieRepository {
    override suspend fun retrieveTrendingMovie() = flow {
        emit(Loading())
        try {
            val movieResponse = movieApiService.retrieveMovie()
            if((movieResponse.totalResults ?: 0) > 0){
                emit(Result.Success(movieResponse.results!!.map { it!!.toDomainModel() }))
            }else{
                emit(Result.Error("No Movie Found"))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun quickSearchMovie(query: String) = flow {
        emit(Loading())
        try {
            val movieResponse = movieApiService.searchMovie(query)
            if((movieResponse.totalResults ?: 0) > 0){
                emit(Result.Success(movieResponse.results!!.map { it!!.toQuickSearchModel() }))
            }else{
                emit(Result.Error("No Movie Found"))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun searchMovie(query: String, includeAdult: Boolean): Flow<Result<List<Movie>>> = flow {
        emit(Loading())
        try {
            val movieResponse = movieApiService.searchMovie(query, includeAdult)
            if((movieResponse.totalResults ?: 0) > 0){
                emit(Result.Success(movieResponse.results!!.map { it!!.toDomainModel() }))
            }else{
                emit(Result.Error("No Movie Found"))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun retrieveMovie(movieId: Int): Flow<Result<Movie>> = flow {
        emit(Loading())
        try {
            val movie = movieApiService.retrieveMovie(movieId)
            emit(Result.Success(movie.toDomainModel()))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun retrieveMovieImages(movieId: Int): Flow<Result<List<MediaSourceInfo>>> = flow {
        emit(Loading())
        try {
            val mediaSourceInfoList = arrayListOf<MediaSourceInfo>()
            val imagesResponse = movieApiService.retrieveMovieImages(movieId)
            mediaSourceInfoList.addAll(
                imagesResponse.backdrops.map {
                    it.toMediaSourceInfo()
                }
            )
//            mediaSourceInfoList.addAll(
//                imageResponse.posters.map {
//                    it.toMediaSourceInfo()
//                }
//            )
            emit(Result.Success(mediaSourceInfoList))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun retrieveMovieVideos(movieId: Int): Flow<Result<List<MediaSourceInfo>>> = flow {
        emit(Loading())
        try {
            val mediaSourceInfoList = arrayListOf<MediaSourceInfo>()
            val videosResponse = movieApiService.retrieveMovieVideos(movieId)
            mediaSourceInfoList.addAll(
                videosResponse.results.map {
                    it.toMediaSourceInfo()
                }
            )
            emit(Result.Success(mediaSourceInfoList))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }
}