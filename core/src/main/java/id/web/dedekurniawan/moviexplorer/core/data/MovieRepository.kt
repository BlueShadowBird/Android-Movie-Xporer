package id.web.dedekurniawan.moviexplorer.core.data

import id.web.dedekurniawan.moviexplorer.core.data.local.room.MovieDao
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.data.remote.retrofit.ApiService
import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IMovieRepository
import id.web.dedekurniawan.moviexplorer.core.utils.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val apiService: ApiService,
    private val dao: MovieDao
): IMovieRepository {
    override suspend fun searchMovie(query: String): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading())
        try {
            val movieResponse = apiService.searchMovie(query)
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
            val movie = apiService.retrieveMovie(movieId)
            emit(Result.Success(movie.toDomainModel()))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun saveGameToFavorite(movie: Movie) {

    }

    override fun deleteFavoriteGame(movieId: String) {

    }

    override fun getAllFavorite(): Flow<List<Movie>> = flow {

    }

    override fun isFavorite(movieId: String): Flow<Boolean> = dao.isFavorited(movieId)
}