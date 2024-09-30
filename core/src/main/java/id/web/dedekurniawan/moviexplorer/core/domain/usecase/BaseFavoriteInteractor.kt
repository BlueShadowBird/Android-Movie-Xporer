package id.web.dedekurniawan.moviexplorer.core.domain.usecase

import com.google.gson.Gson
import id.web.dedekurniawan.moviexplorer.core.domain.model.Favorite
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IFavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseFavoriteInteractor<T>(
    private val favoriteRepository: IFavoriteRepository
): FavoriteUseCase<T> {
    protected val gson: Gson = Gson()
    abstract val moduleName: String

    abstract fun convertFromJson(favorite: Favorite): T
    abstract fun createFavoriteObject(data: T): Favorite

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavorites(): Flow<List<T>> {
        return favoriteRepository.getFavoritesByModuleName(moduleName)
            .mapLatest { favoriteList ->
                favoriteList.map { favorite ->
                    convertFromJson(favorite)
                }
            }
    }

    override fun isFavorite(moduleItemId: Int): Flow<Boolean> {
        return favoriteRepository.isFavorite(moduleName, moduleItemId)
    }

    override fun saveFavorite(data: T) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                favoriteRepository.saveFavorite(createFavoriteObject(data))
            }
        }
    }

    override fun deleteFavorite(data: T) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                favoriteRepository.deleteFavorite(createFavoriteObject(data))
            }
        }
    }
}
