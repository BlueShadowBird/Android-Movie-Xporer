package id.web.dedekurniawan.moviexplorer.core.data.local.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "favorite",
    primaryKeys = ["moduleItemId", "moduleName"],
    indices = [Index(value = ["moduleItemId", "moduleName"], unique = true)]
)
class FavoriteEntity(
    val moduleName: String,
    val moduleItemId: Int,
    val searchKey: String?,      // for search for next improvement
    val data: String?
)