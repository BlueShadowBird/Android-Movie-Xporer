package id.web.dedekurniawan.moviexplorer.core.data.di


import androidx.room.Room
import id.web.dedekurniawan.moviexplorer.core.BuildConfig
import id.web.dedekurniawan.moviexplorer.core.data.MovieRepository
import id.web.dedekurniawan.moviexplorer.core.data.SettingRepository
import id.web.dedekurniawan.moviexplorer.core.data.local.room.MovieDatabase
import id.web.dedekurniawan.moviexplorer.core.data.remote.retrofit.ApiService
import id.web.dedekurniawan.moviexplorer.core.domain.repository.IMovieRepository
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

val networkModule = module {

    single {
        val properties = Properties()
        properties.load(androidContext().assets.open("api.properties"))

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
                val original = it.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("api_key", properties.getProperty("key"))
                    .build()

                val request = original.newBuilder().url(url).build()
                it.proceed(request)
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
            .client(get())
            .build().create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { get<MovieDatabase>().getDao() }
    single {
//        val passphrase: ByteArray = SQLiteDatabase.getBytes("SteamGameExplorer".toCharArray())
//        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Favorite.db").fallbackToDestructiveMigration()
//            .openHelperFactory(factory)
            .build()
    }
    single<IMovieRepository> { MovieRepository(get(), get()) }
    single<ISettingRepository> { SettingRepository(get()) }
}