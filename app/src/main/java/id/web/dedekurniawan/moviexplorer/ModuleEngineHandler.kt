package id.web.dedekurniawan.moviexplorer

import id.web.dedekurniawan.moviexplorer.core.data.di.KoinModuleProvider
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.core.domain.SettingModuleChangeHandler
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_PERSON_MODULE
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules


class ModuleEngineHandler(
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
    fun initMovieModule(){
        initFromPersonKoinModuleProvider(
            "id.web.dedekurniawan.moviexplorer.movie.domain.MovieModuleElementProvider"
        )
    }

    fun initPersonModule(){
        initFromPersonKoinModuleProvider(
            "id.web.dedekurniawan.moviexplorer.person.domain.PersonModuleElementProvider"
        )
    }

    private fun initFromPersonKoinModuleProvider(providerClassName: String){
        val moduleElementClass = Class.forName(providerClassName)
        val moduleElementProvider = moduleElementClass.getDeclaredConstructor().newInstance() as ModuleEngine.ModuleElementProvider

        moduleEngine.addModuleElement(moduleElementProvider.getModuleElement())

        notifyAllListener()
    }

    override fun moduleEnabled(moduleName: String) {
        when(moduleName){
            KEY_PERSON_MODULE -> {
                val koinModuleProvider = Class.forName(
                    "id.web.dedekurniawan.moviexplorer.person.data.di.PersonKoinModuleProvider"
                ).getDeclaredConstructor().newInstance() as KoinModuleProvider

                loadKoinModules(koinModuleProvider.getModule())

                initPersonModule()
            }
        }
    }

    private fun notifyAllListener(){
        moduleEngineUpdateListenerSet.forEach {
            it.moduleEngineUpdated()
        }
    }

    interface ModuleEngineUpdateListener{
        fun moduleEngineUpdated()
    }
}