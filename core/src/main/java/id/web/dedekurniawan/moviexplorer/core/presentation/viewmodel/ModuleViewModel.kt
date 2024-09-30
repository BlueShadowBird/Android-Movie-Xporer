package id.web.dedekurniawan.moviexplorer.core.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import kotlinx.coroutines.launch

abstract class ModuleViewModel<T>(protected val useCase: ModuleElement.ModuleUseCase<T>): ViewModel() {
    abstract val quickSearchResult: LiveData<Result<List<QuickSearchBaseModel>>>
    abstract fun quickSearch(query: String)

    abstract fun retrieveTrending()
    abstract fun search(query: String)

    private val _isFavoriteResult = MediatorLiveData<Boolean>()
    val isFavoriteResult: LiveData<Boolean> = _isFavoriteResult

    protected val localListResult = MediatorLiveData<Result<List<T>>>()
    val listResult: LiveData<Result<List<T>>> = localListResult

    fun getFavorites(){
        localListResult.addSource(useCase.getFavorites().asLiveData()){
            localListResult.value = Result.Success(it)
        }
    }

    fun listenFavorite(moduleItemId: Int){
        _isFavoriteResult.addSource(useCase.isFavorite(moduleItemId).asLiveData()){
            _isFavoriteResult.value = it
        }
    }

    fun saveToFavorite(data: T) {
        viewModelScope.launch { useCase.saveFavorite(data) }
    }

    fun deleteFavorite(data: T) {
        viewModelScope.launch { useCase.deleteFavorite(data) }
    }
}