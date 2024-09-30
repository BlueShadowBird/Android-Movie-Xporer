package id.web.dedekurniawan.moviexplorer.core.domain.repository

import id.web.dedekurniawan.moviexplorer.core.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun getFavoritesByModuleName(moduleName: String): Flow<List<Favorite>>
    fun isFavorite(moduleName: String, moduleItemId: Int): Flow<Boolean>
    fun saveFavorite(favorite: Favorite)
    fun deleteFavorite(favorite: Favorite)
}