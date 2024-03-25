package id.web.dedekurniawan.moviexplorer.person.data

import id.web.dedekurniawan.moviexplorer.person.data.remote.PersonApiService
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.domain.model.PersonQuickSearchModel
import id.web.dedekurniawan.moviexplorer.person.domain.repository.IPersonRepository
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.person.utils.toDomainModel
import id.web.dedekurniawan.moviexplorer.person.utils.toQuickSearchModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PersonRepository(
    private val apiService: PersonApiService
): IPersonRepository {
    override suspend fun retrieveTrendingPerson() = flow {
        emit(Result.Loading())
        try {
            val movieResponse = apiService.retrievePeople()
            if((movieResponse.totalResults ?: 0) > 0){
                emit(Result.Success(movieResponse.results!!.map { it.toDomainModel() }))
            }else{
                emit(Result.Error("No Person Found"))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun quickSearchPerson(query: String): Flow<Result<List<PersonQuickSearchModel>>> = flow {
        emit(Result.Loading())
        try {
            val movieResponse = apiService.searchPerson(query)
            if((movieResponse.totalResults ?: 0) > 0){
                emit(Result.Success(movieResponse.results!!.map { it.toQuickSearchModel() }))
            }else{
                emit(Result.Error("No Person Found"))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun searchPerson(
        query: String,
        includeAdult: Boolean
    ): Flow<Result<List<Person>>>  = flow {
        emit(Result.Loading())
        try {
            val movieResponse = apiService.searchPerson(query, includeAdult)
            if((movieResponse.totalResults ?: 0) > 0){
                emit(Result.Success(movieResponse.results!!.map { it.toDomainModel() }))
            }else{
                emit(Result.Error("No Movie Found"))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    override suspend fun retrievePerson(personId: Int): Flow<Result<Person>> = flow {
        emit(Result.Loading())
        try {
            val person = apiService.retrievePerson(personId)
            emit(Result.Success(person.toDomainModel()))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }
}