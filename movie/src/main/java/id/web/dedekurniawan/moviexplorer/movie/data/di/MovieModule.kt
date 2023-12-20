package id.web.dedekurniawan.moviexplorer.movie.data.di


import id.web.dedekurniawan.moviexplorer.movie.adapter.MovieAdapter
import id.web.dedekurniawan.moviexplorer.movie.data.MovieRepository
import id.web.dedekurniawan.moviexplorer.movie.data.remote.MovieApiService
import id.web.dedekurniawan.moviexplorer.movie.domain.repository.IMovieRepository
import id.web.dedekurniawan.moviexplorer.movie.domain.usecase.MovieInteractor
import id.web.dedekurniawan.moviexplorer.movie.domain.usecase.MovieUseCase
import id.web.dedekurniawan.moviexplorer.movie.presentation.viewmodel.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.Properties

val movieKoinModule = module {
    single {
        val properties = Properties()
        properties.load(androidContext().assets.open("api.properties"))

        get<Retrofit.Builder>()
            .client(get())
            .build().create(MovieApiService::class.java)
    }

    single<IMovieRepository> {
        MovieRepository(
            get(),
            get()
        )
    }

    single<MovieUseCase> {
        MovieInteractor(
            get(),
            get()
        )
    }

    factory { MovieAdapter() }

    viewModel { MovieViewModel(get()) }


}