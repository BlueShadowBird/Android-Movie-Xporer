package id.web.dedekurniawan.moviexplorer

import androidx.preference.PreferenceManager
import id.web.dedekurniawan.moviexplorer.core.data.di.*
import id.web.dedekurniawan.moviexplorer.core.utils.changeTheme
import id.web.dedekurniawan.moviexplorer.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieXplorerApplication: com.google.android.play.core.splitcompat.SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieXplorerApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    adapterModule,
                    viewModelModule,
                    useCaseModule
                )
            )
        }

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        changeTheme(sharedPref.getString("theme", null)?.toInt())
    }
}