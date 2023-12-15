package id.web.dedekurniawan.moviexplorer.core.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel

abstract class ModuleViewModel<T>: ViewModel() {
    abstract val quickSearchResult: LiveData<Result<List<QuickSearchBaseModel>>>
    abstract fun quickSearch(query: String)

    abstract val listResult: LiveData<Result<List<T>>>
    abstract fun retrieveTrending()
    abstract fun search(query: String)
}