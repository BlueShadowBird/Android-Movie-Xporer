package id.web.dedekurniawan.moviexplorer.presentation.viewmodel

import androidx.lifecycle.*
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.Movie
import id.web.dedekurniawan.moviexplorer.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class MovieViewModel(private val useCase: MovieUseCase): ViewModel() {
    private val _movie = MediatorLiveData<Result<Movie>>()
    val movie: LiveData<Result<Movie>> = _movie

    fun retrieveMovie(movieId: Int){
        viewModelScope.launch {
            _movie.addSource(useCase.retrieveMovie(movieId).asLiveData()){
                _movie.value = it
            }
        }
    }
}