package id.web.dedekurniawan.moviexplorer

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.play.core.splitcompat.SplitCompat
import id.web.dedekurniawan.moviexplorer.di.appKoinModule
import id.web.dedekurniawan.moviexplorer.core.data.di.*
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_PERSON_MODULE
import id.web.dedekurniawan.moviexplorer.core.utils.changeTheme
import id.web.dedekurniawan.moviexplorer.core.utils.isInstalled
import id.web.dedekurniawan.moviexplorer.movie.data.di.movieKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieXplorerApplication: com.google.android.play.core.splitcompat.SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieXplorerApplication)
            modules(
                mutableListOf(
                    networkKoinModule,
                    repositoryKoinModule,
                    engineKoinModule,
                    movieKoinModule,
                    appKoinModule
                ).apply {
                    if(isInstalled(applicationContext, KEY_PERSON_MODULE)){
                        val koinModuleProvider = Class.forName("id.web.dedekurniawan.moviexplorer.person.data.di.PersonModuleProvider").getDeclaredConstructor().newInstance() as KoinModuleProvider
                        add(koinModuleProvider.getModule())
                    }
                }
            )
        }

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        changeTheme(sharedPref.getString("theme", null)?.toInt())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}