package id.web.dedekurniawan.moviexplorer.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.web.dedekurniawan.moviexplorer.core.data.local.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM favorite WHERE movie_id = :movieId")
    fun getFavoriteMovie(movieId: String): Flow<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovies(movies: List<FavoriteMovieEntity>)

    @Query("DELETE FROM favorite")
    fun deleteFavorites()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("DELETE FROM favorite WHERE movie_id = :movieId")
    fun deleteFavoriteMovie(movieId: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE movie_id = :movieId)")
    fun isFavorited(movieId: String): Flow<Boolean>
}