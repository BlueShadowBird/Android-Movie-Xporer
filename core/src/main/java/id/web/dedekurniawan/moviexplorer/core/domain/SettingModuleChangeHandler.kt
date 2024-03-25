package id.web.dedekurniawan.moviexplorer.core.domain

class SettingModuleChangeHandler{
    private val settingModuleChangeListenerSet = mutableSetOf<SettingModuleChangeListener>()

    fun addSettingModuleChangeListener(listener: SettingModuleChangeListener){
        settingModuleChangeListenerSet.add(listener)
    }

    fun removeSettingModuleChangeListener(listener: SettingModuleChangeListener){
        settingModuleChangeListenerSet.remove(listener)
    }

    fun notifyListener(moduleName: String){
        settingModuleChangeListenerSet.forEach{ listener ->
            listener.moduleEnabled(moduleName)
        }
    }

    interface SettingModuleChangeListener {
        fun moduleEnabled(moduleName: String)
    }
}