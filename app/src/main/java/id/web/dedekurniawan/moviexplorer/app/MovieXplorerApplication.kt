package id.web.dedekurniawan.moviexplorer.app

import androidx.preference.PreferenceManager
import id.web.dedekurniawan.moviexplorer.app.di.appKoinModule
import id.web.dedekurniawan.moviexplorer.core.data.di.*
import id.web.dedekurniawan.moviexplorer.core.utils.changeTheme
import id.web.dedekurniawan.moviexplorer.movie.data.di.movieKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieXplorerApplication: com.google.android.play.core.splitcompat.SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieXplorerApplication)
            modules(
                listOf(
                    networkKoinModule,
                    repositoryKoinModule,
                    engineKoinModule,
                    movieKoinModule,
                    appKoinModule
                )
            )
        }

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        changeTheme(sharedPref.getString("theme", null)?.toInt())
    }
}