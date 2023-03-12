package id.web.dedekurniawan.moviexplorer.di

import id.web.dedekurniawan.moviexplorer.presentation.viewmodel.SearchViewModel
import id.web.dedekurniawan.moviexplorer.core.adapter.MovieAdapter
import id.web.dedekurniawan.moviexplorer.core.domain.usecase.MovieInteractor
import id.web.dedekurniawan.moviexplorer.core.domain.usecase.MovieUseCase
import id.web.dedekurniawan.moviexplorer.presentation.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val adapterModule = module {
    factory { MovieAdapter() }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { MovieViewModel(get()) }
}