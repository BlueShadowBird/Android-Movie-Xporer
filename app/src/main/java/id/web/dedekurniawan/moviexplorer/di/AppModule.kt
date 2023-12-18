package id.web.dedekurniawan.moviexplorer.di

import id.web.dedekurniawan.moviexplorer.ModuleEngineHandler
import id.web.dedekurniawan.moviexplorer.presentation.viewmodel.QuicksearchVIewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appKoinModule = module {
    single {
        ModuleEngineHandler(
            androidContext(),
            get()
        ).apply {
            initMovieModule(
                get(),
                get()
            )
        }
    }

    viewModel {
        QuicksearchVIewModel(
            get()
        )
    }
}