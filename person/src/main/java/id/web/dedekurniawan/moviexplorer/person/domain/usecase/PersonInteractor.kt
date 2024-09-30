package id.web.dedekurniawan.moviexplorer.person.domain.usecase

import com.google.gson.Gson
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.Favorite
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IFavoriteRepository
import id.web.dedekurniawan.moviexplorer.core.domain.repository.ISettingRepository
import id.web.dedekurniawan.moviexplorer.core.domain.usecase.BaseFavoriteInteractor
import id.web.dedekurniawan.moviexplorer.person.common.MODULE_NAME
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.domain.repository.IPersonRepository
import kotlinx.coroutines.flow.Flow

class PersonInteractor(
    private val repository: IPersonRepository,
    private val settingRepository: ISettingRepository,
    favoriteRepository: IFavoriteRepository
): PersonUseCase, BaseFavoriteInteractor<Person>(favoriteRepository) {
    override suspend fun quickSearch(query: String) = repository.quickSearchPerson(query) as Flow<Result<List<QuickSearchBaseModel>>>

    override suspend fun search(query: String) = repository.searchPerson(query, settingRepository.getIncludeAdultSetting())

    override suspend fun retrieveTrending() = repository.retrieveTrendingPerson()

    override suspend fun retrieveDetail(itemId: Int) = repository.retrievePerson(itemId)

    override val moduleName = MODULE_NAME

    override fun convertFromJson(favorite: Favorite): Person = gson.fromJson(favorite.data, Person::class.java)

    override fun createFavoriteObject(data: Person) = Favorite(
        MODULE_NAME,
        data.id,
        data.name,
        Gson().toJson(Person(
            data.id,
            data.name,
            profilePath = data.profilePath,
            gender = data.gender,
            knownForDepartment = data.knownForDepartment,
            popularity = data.popularity
        ))
    )
}