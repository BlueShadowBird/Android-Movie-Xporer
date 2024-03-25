package id.web.dedekurniawan.moviexplorer

import android.content.Context
import id.web.dedekurniawan.moviexplorer.core.data.di.KoinModuleProvider
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.core.domain.SettingModuleChangeHandler
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_PERSON_MODULE
import id.web.dedekurniawan.moviexplorer.movie.domain.MovieModuleElement
import id.web.dedekurniawan.moviexplorer.movie.domain.usecase.MovieUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.loadKoinModules


open class ModuleEngineHandler(
    context: Context,
    private val settingModuleChangeHandler: SettingModuleChangeHandler,
    val moduleEngine: ModuleEngine
): SettingModuleChangeHandler.SettingModuleChangeListener, KoinComponent  {
    private val moduleEngineUpdateListenerSet = mutableSetOf<ModuleEngineUpdateListener>()

    fun addModuleEngineUpdateListener(listener: ModuleEngineUpdateListener) {
        moduleEngineUpdateListenerSet.add(listener)
    }

    fun removeModuleEngineUpdateListener(listener: ModuleEngineUpdateListener) {
        moduleEngineUpdateListenerSet.remove(listener)
    }
    init {
        settingModuleChangeHandler.addSettingModuleChangeListener(this)
    }

    protected fun finalize() {
        settingModuleChangeHandler.removeSettingModuleChangeListener(this)
    }
    fun initMovieModule(useCase: MovieUseCase){
        val moduleElement = MovieModuleElement(
            useCase
        )
        moduleEngine.addModuleElement(moduleElement)
    }

    fun initPersonModule(useCase: MovieUseCase){
        val moduleElementClass = Class.forName("id.web.dedekurniawan.moviexplorer.person.domain.PersonModuleElement")
        val moduleElement = moduleElementClass.getDeclaredConstructor(ModuleElement.ModuleUseCase::class.java).newInstance(useCase)

        moduleEngine.addModuleElement(moduleElement as ModuleElement)

        notifyAllListener()
    }

    override fun moduleEnabled(moduleName: String) {
        when(moduleName){
            KEY_PERSON_MODULE -> {
                val koinModuleProvider = Class.forName("id.web.dedekurniawan.moviexplorer.person.data.di.PersonModuleProvider").getDeclaredConstructor().newInstance() as KoinModuleProvider
                loadKoinModules(koinModuleProvider.getModule())

                initPersonModule(get())
            }
        }
    }

    protected fun notifyAllListener(){
        moduleEngineUpdateListenerSet.forEach {
            it.moduleEngineUpdated()
        }
    }

    interface ModuleEngineUpdateListener{
        fun moduleEngineUpdated()
    }
}