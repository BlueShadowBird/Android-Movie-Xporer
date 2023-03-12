package id.web.dedekurniawan.moviexplorer.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.web.dedekurniawan.moviexplorer.core.data.local.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getDao(): MovieDao
}