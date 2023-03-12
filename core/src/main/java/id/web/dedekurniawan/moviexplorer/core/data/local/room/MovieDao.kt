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

    @Query("SELECT * FROM favorite WHERE movie_id = :movie_id")
    fun getFavoriteMovie(movie_id: String): Flow<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovies(Movies: List<FavoriteMovieEntity>)

    @Query("DELETE FROM favorite")
    fun deleteFavorites()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Query("DELETE FROM favorite WHERE movie_id = :movie_id")
    fun deleteFavoriteMoview(movie_id: String)

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE movie_id = :movie_id)")
    fun isFavorited(movie_id: String): Flow<Boolean>
}