package id.web.dedekurniawan.moviexplorer.person.domain.repository

import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.domain.model.PersonQuickSearchModel
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import kotlinx.coroutines.flow.Flow

interface IPersonRepository {
    suspend fun retrieveTrendingPerson(): Flow<Result<List<Person>>>
    suspend fun quickSearchPerson(query: String): Flow<Result<List<PersonQuickSearchModel>>>
    suspend fun searchPerson(query: String, includeAdult: Boolean = false): Flow<Result<List<Person>>>
    suspend fun retrievePerson(personId: Int): Flow<Result<Person>>
}