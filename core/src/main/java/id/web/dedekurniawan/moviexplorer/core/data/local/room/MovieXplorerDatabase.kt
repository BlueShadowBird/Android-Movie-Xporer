package id.web.dedekurniawan.moviexplorer.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.web.dedekurniawan.moviexplorer.core.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MovieXplorerDatabase: RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}