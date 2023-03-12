package id.web.dedekurniawan.moviexplorer.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteMovieEntity(
    @field:ColumnInfo(name = "movie_id")
    @field:PrimaryKey
    val id: String
)