package id.web.dedekurniawan.moviexplorer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class QuicksearchVIewModel(private val moduleEngine: ModuleEngine): ViewModel() {
    private val _quickSearchResult = MutableStateFlow(Pair("", ""))
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val quickSearchResult: LiveData<Result<List<QuickSearchBaseModel>>> = _quickSearchResult
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.second.trim().length > 2
        }
        .mapLatest {
            moduleEngine.moduleElementMap[it.first]!!.moduleUseCase.quickSearch(it.second)
        }
        .flatMapConcat { it }
        .asLiveData()

    fun quickSearch(moduleName: String, query: String) {
        viewModelScope.launch {
            _quickSearchResult.value = Pair(moduleName, query)
        }
    }
}