package id.web.dedekurniawan.moviexplorer.core.domain

import androidx.fragment.app.Fragment
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import kotlinx.coroutines.flow.Flow


class ModuleEngine{
    val moduleElementList = linkedMapOf<String, ModuleElement>()

    fun addModuleElement(moduleElement: ModuleElement): ModuleEngine {
        moduleElementList[moduleElement.name] = moduleElement
        return this
    }
}

class ModuleElement(val name: String, val dashboardModule: DashboardModule, val moduleUseCase: ModuleUseCase<Any>) {
    interface ModuleUseCase<T>{
        suspend fun quickSearch(query: String): Flow<Result<List<QuickSearchBaseModel>>>
        suspend fun search(query: String): Flow<Result<List<T>>>
        suspend fun retrieveTrending(): Flow<Result<List<T>>>
        suspend fun retrieveDetail(movieId: Int): Flow<Result<T>>
    }

    abstract class DashboardModule{
        abstract val itemMenuId: Int
        abstract fun createFragment(): Fragment
    }
}

