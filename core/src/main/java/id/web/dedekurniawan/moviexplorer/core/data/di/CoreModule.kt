package id.web.dedekurniawan.moviexplorer.core.data.di

import androidx.room.Room
import id.web.dedekurniawan.moviexplorer.core.BuildConfig
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleEngine
import id.web.dedekurniawan.moviexplorer.core.data.SettingRepository
import id.web.dedekurniawan.moviexplorer.core.data.local.room.MovieDatabase
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
//            .add("steamcommunity.com", BuildConfig.STEAMCOMMUNITY_CERTIFICATE)
//            .add("store.steampowered.com", BuildConfig.STEAMSTORE_CERTIFICATE)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(
                if(BuildConfig.DEBUG)
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            ).certificatePinner(certificatePinner)
            .addInterceptor {
//                val original = it.request()
//                val url = original.url.newBuilder()
//                    .addQueryParameter("api_key", properties.getProperty("key"))
//                    .build()
//
//                val request = original.newBuilder().url(url).build()
//                it.proceed(request)

                val builder = it.request().newBuilder()


                builder
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer $key")
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
    single { get<MovieDatabase>().getMovieDao() }
    single {
//        val passphrase: ByteArray = SQLiteDatabase.getBytes("MovieXplorer".toCharArray())
//        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Favorite.db").fallbackToDestructiveMigration()
//            .openHelperFactory(factory)
            .build()
    }
    single<ISettingRepository> { SettingRepository(get()) }
}

val engineKoinModule = module {
    single {
        ModuleEngine()
    }
}