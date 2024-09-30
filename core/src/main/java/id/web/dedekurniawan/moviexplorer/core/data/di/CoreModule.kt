package id.web.dedekurniawan.moviexplorer.core.data.di

import androidx.room.Room
import id.web.dedekurniawan.moviexplorer.core.BuildConfig
import id.web.dedekurniawan.moviexplorer.core.data.local.room.MovieXplorerDatabase
import id.web.dedekurniawan.moviexplorer.core.data.repository.FavoriteRepository
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.core.data.repository.SettingRepository
import id.web.dedekurniawan.moviexplorer.core.domain.SettingModuleChangeHandler
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IFavoriteRepository
import id.web.dedekurniawan.moviexplorer.core.domain.repository.ISettingRepository
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties
import java.util.concurrent.TimeUnit

val networkKoinModule = module {
    single {
        val properties = Properties()
        properties.load(androidContext().assets.open("api.properties"))
        val key = properties.getProperty("key")

        val certificatePinner = CertificatePinner.Builder()
//            .add("api.themoviedb.org", BuildConfig.TMDB_CERTIFICATE)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(
                if(BuildConfig.DEBUG)
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            ).certificatePinner(certificatePinner)
            .addInterceptor {
                val builder = it.request().newBuilder().apply {
                    addHeader("accept", "application/json")
                    addHeader("Authorization", "Bearer $key")
                }
                it.proceed(builder.build())
            }
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    single {
        val properties = Properties()
        properties.load(androidContext().assets.open("api.properties"))

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(properties.getProperty("url"))
    }
}

val repositoryKoinModule = module {
    single { get<MovieXplorerDatabase>().getFavoriteDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieXplorerDatabase::class.java, "MovieXplorer.db"
        ).fallbackToDestructiveMigration().build()
    }

    single<IFavoriteRepository> { FavoriteRepository(get()) }
    single<ISettingRepository> { SettingRepository(get()) }
}

val engineKoinModule = module {
    single {
        SettingModuleChangeHandler()
    }

    single {
        ModuleEngine()
    }
}