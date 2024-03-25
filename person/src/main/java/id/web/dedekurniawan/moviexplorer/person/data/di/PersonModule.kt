package id.web.dedekurniawan.moviexplorer.person.data.di

import id.web.dedekurniawan.moviexplorer.core.data.di.KoinModuleProvider
import id.web.dedekurniawan.moviexplorer.person.adapter.PersonAdapter
import id.web.dedekurniawan.moviexplorer.person.data.PersonRepository
import id.web.dedekurniawan.moviexplorer.person.data.remote.PersonApiService
import id.web.dedekurniawan.moviexplorer.person.domain.repository.IPersonRepository
import id.web.dedekurniawan.moviexplorer.person.domain.usecase.PersonInteractor
import id.web.dedekurniawan.moviexplorer.person.domain.usecase.PersonUseCase
import id.web.dedekurniawan.moviexplorer.person.presentation.viewmodel.PersonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.Properties

val personKoinModule = module {
    single {
        val properties = Properties()
        properties.load(androidContext().assets.open("api.properties"))

        get<Retrofit.Builder>()
            .client(get())
            .build().create(PersonApiService::class.java)
    }

    single<IPersonRepository> {
        PersonRepository(
            get()
        )
    }

    single<PersonUseCase> {
        PersonInteractor(
            get(),
            get()
        )
    }

    factory { PersonAdapter() }

    viewModel { PersonViewModel(get()) }
}

class PersonModuleProvider: KoinModuleProvider {
    override fun getModule() = personKoinModule
}