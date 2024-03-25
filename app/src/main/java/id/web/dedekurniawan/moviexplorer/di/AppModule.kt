package id.web.dedekurniawan.moviexplorer.di

import id.web.dedekurniawan.moviexplorer.ModuleEngineHandler
import id.web.dedekurniawan.moviexplorer.core.utils.KEY_PERSON_MODULE
import id.web.dedekurniawan.moviexplorer.core.utils.isInstalled
import id.web.dedekurniawan.moviexplorer.presentation.viewmodel.QuicksearchVIewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appKoinModule = module {
    single {
        ModuleEngineHandler(
            androidContext(),
            get(),
            get()
        ).apply {
            initMovieModule(
                get()
            )
            if(isInstalled(androidContext(), KEY_PERSON_MODULE)){
                initPersonModule(
                    get()
                )
            }
        }
    }

    viewModel {
        QuicksearchVIewModel(
            get()
        )
    }
}