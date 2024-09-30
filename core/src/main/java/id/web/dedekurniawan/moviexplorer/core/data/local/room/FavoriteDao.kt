package id.web.dedekurniawan.moviexplorer.core.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.web.dedekurniawan.moviexplorer.core.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE moduleName = :moduleName")
    fun getFavoriteByModuleName(moduleName: String): Flow<List<FavoriteEntity>>

    @Query("SELECT moduleItemId FROM favorite WHERE moduleItemId IN (:moduleItemIds)")
    fun getExistingModuleItemIds(moduleItemIds: List<Int>): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity): Long

    @Query("SELECT EXISTS(SELECT 1 FROM favorite WHERE moduleItemId = :moduleItemId AND moduleName = :moduleName)")
    suspend fun isFavoriteExists(moduleName: String, moduleItemId: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM favorite WHERE moduleItemId = :moduleItemId AND moduleName = :moduleName)")
    fun listenFavorite(moduleName: String, moduleItemId: Int): Flow<Boolean>

    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE moduleItemId = :moduleItemId AND moduleName = :moduleName")
    fun deleteFavoriteByIdAndName(moduleItemId: Int, moduleName: String)
}