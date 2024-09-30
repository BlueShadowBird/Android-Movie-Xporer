package id.web.dedekurniawan.moviexplorer.core.utils

import id.web.dedekurniawan.moviexplorer.core.data.local.entity.FavoriteEntity
import id.web.dedekurniawan.moviexplorer.core.domain.model.Favorite

fun FavoriteEntity.toDomainModel() = Favorite(
    moduleName,
    moduleItemId,
    searchKey,
    data
)

fun Favorite.toEntityModel() = FavoriteEntity(
    moduleName,
    moduleItemId,
    searchKey,
    data
)