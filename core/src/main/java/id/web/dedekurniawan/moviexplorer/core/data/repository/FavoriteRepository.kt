package id.web.dedekurniawan.moviexplorer.core.data.repository

import id.web.dedekurniawan.moviexplorer.core.data.local.room.FavoriteDao
import id.web.dedekurniawan.moviexplorer.core.domain.model.Favorite
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IFavoriteRepository
import id.web.dedekurniawan.moviexplorer.core.utils.toDomainModel
import id.web.dedekurniawan.moviexplorer.core.utils.toEntityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class FavoriteRepository(
    private val dao: FavoriteDao
): IFavoriteRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavoritesByModuleName(moduleName: String) = dao.getFavoriteByModuleName(moduleName)
        .mapLatest { favoriteEntityList ->
            favoriteEntityList.map { favoriteEntity ->
                favoriteEntity.toDomainModel()
            }
        }

    override fun isFavorite(moduleName: String, moduleItemId: Int): Flow<Boolean> = dao.listenFavorite(moduleName, moduleItemId)

    override fun saveFavorite(favorite: Favorite){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertFavorite(
                favorite.toEntityModel()
            )
        }
    }

    override fun deleteFavorite(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteFavorite(
                favorite.toEntityModel()
            )
        }
    }

}