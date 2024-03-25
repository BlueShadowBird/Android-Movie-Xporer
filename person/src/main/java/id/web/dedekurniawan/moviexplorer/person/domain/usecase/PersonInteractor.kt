package id.web.dedekurniawan.moviexplorer.person.domain.usecase

import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.core.domain.repository.ISettingRepository
import id.web.dedekurniawan.moviexplorer.person.domain.repository.IPersonRepository
import kotlinx.coroutines.flow.Flow

class PersonInteractor(
    private val repository: IPersonRepository,
    private val settingRepository: ISettingRepository
): PersonUseCase {
    override suspend fun quickSearch(query: String) = repository.quickSearchPerson(query) as Flow<Result<List<QuickSearchBaseModel>>>

    override suspend fun search(query: String) = repository.searchPerson(query, settingRepository.getIncludeAdultSetting())

    override suspend fun retrieveTrending() = repository.retrieveTrendingPerson()

    override suspend fun retrieveDetail(itemId: Int) = repository.retrievePerson(itemId)
}