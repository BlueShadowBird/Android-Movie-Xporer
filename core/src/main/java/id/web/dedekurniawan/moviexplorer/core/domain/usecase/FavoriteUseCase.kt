package id.web.dedekurniawan.moviexplorer.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase<T> {
    fun getFavorites(): Flow<List<T>>
    fun isFavorite(moduleItemId: Int): Flow<Boolean>
    fun saveFavorite(data: T)
    fun deleteFavorite(data: T)
}
