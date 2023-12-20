package id.web.dedekurniawan.moviexplorer.core.domain

import android.content.Context
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

abstract class ModuleElement(val moduleUseCase: ModuleUseCase<*>) {
    abstract val name: String
    abstract val itemMenuId: Int
    abstract fun createFragment(): Fragment
    abstract fun startActivity(context: Context, itemId: Int)


    interface ModuleUseCase<T>{
        suspend fun quickSearch(query: String): Flow<Result<List<QuickSearchBaseModel>>>
        suspend fun search(query: String): Flow<Result<List<T>>>
        suspend fun retrieveTrending(): Flow<Result<List<T>>>
        suspend fun retrieveDetail(movieId: Int): Flow<Result<T>>
    }
}

