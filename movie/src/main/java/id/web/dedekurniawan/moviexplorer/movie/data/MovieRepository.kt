package id.web.dedekurniawan.moviexplorer.movie.data

import id.web.dedekurniawan.moviexplorer.core.data.local.room.MovieDao
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.movie.data.remote.MovieApiService
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.domain.repository.IMovieRepository
import id.web.dedekurniawan.moviexplorer.movie.utils.toDomainModel
import id.web.dedekurniawan.moviexplorer.movie.utils.toQuickSearchModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val movieApiService: MovieApiService,
    private val dao: MovieDao
): IMovieRepository {
    override suspend fun retrieveTrendingMovie() = flow {
        emit(Result.Loading())
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
        emit(Result.Loading())
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
        emit(Result.Loading())
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
        emit(Result.Loading())
        try {
            val movie = movieApiService.retrieveMovie(movieId)
            emit(Result.Success(movie.toDomainModel()))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun saveMovieToFavorite(movie: Movie) {

    }

    override fun deleteFavoriteMovie(movieId: String) {

    }

    override fun getAllFavorite(): Flow<List<Movie>> = flow {

    }

    override fun isFavorite(movieId: String): Flow<Boolean> = dao.isFavorited(movieId)
}