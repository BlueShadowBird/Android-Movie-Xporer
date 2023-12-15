package id.web.dedekurniawan.moviexplorer.movie.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.core.presentation.viewmodel.ModuleViewModel
import id.web.dedekurniawan.moviexplorer.movie.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.movie.domain.usecase.MovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class MovieViewModel(private val useCase: MovieUseCase): ModuleViewModel<Movie>() {
    private val _quickSearchResult = MutableStateFlow("")
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override val quickSearchResult: LiveData<Result<List<QuickSearchBaseModel>>> = _quickSearchResult
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().length > 2
        }
        .mapLatest { query ->
            useCase.quickSearch(query)
        }
        .flatMapConcat { it }
        .asLiveData()

    private val _listResult = MediatorLiveData<Result<List<Movie>>>()
    override val listResult: LiveData<Result<List<Movie>>> = _listResult

    private val _result = MediatorLiveData<Result<Movie>>()
    val result: LiveData<Result<Movie>> = _result

    override fun quickSearch(query: String) {
        viewModelScope.launch {
            _quickSearchResult.value = query
        }
    }

    override fun search(query: String){
        viewModelScope.launch {
            _listResult.addSource(useCase.search(query).asLiveData()){
                _listResult.value = it
            }
        }
    }

    override fun retrieveTrending(){
        viewModelScope.launch {
            _listResult.addSource(useCase.retrieveTrending().asLiveData()){
                _listResult.value = it
            }
        }
    }

    fun retrieveMovie(movieId: Int){
        viewModelScope.launch {
            _result.addSource(useCase.retrieveDetail(movieId).asLiveData()){
                _result.value = it
            }
        }
    }
}