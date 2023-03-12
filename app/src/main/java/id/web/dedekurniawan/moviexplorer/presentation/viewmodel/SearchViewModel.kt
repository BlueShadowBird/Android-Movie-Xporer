package id.web.dedekurniawan.moviexplorer.presentation.viewmodel

import androidx.lifecycle.*
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class SearchViewModel(private val useCase: MovieUseCase): ViewModel() {
    private val _searchResult = MediatorLiveData<Result<List<Movie>>>()
    val searchResult: LiveData<Result<List<Movie>>> = _searchResult

    fun search(query: String){
        viewModelScope.launch {
            _searchResult.addSource(useCase.searchMovie(query).asLiveData()){
                _searchResult.value = it
            }
        }
    }
}