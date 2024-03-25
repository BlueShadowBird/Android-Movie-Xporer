package id.web.dedekurniawan.moviexplorer.person.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.core.presentation.viewmodel.ModuleViewModel
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.domain.usecase.PersonUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class PersonViewModel(private val useCase: PersonUseCase): ModuleViewModel<Person>() {
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

    private val _listResult = MediatorLiveData<Result<List<Person>>>()
    override val listResult: LiveData<Result<List<Person>>> = _listResult

    private val _result = MediatorLiveData<Result<Person>>()
    val result: LiveData<Result<Person>> = _result

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

    fun retrievePerson(personId: Int){
        viewModelScope.launch {
            _result.addSource(useCase.retrieveDetail(personId).asLiveData()){
                _result.value = it
            }
        }
    }
}