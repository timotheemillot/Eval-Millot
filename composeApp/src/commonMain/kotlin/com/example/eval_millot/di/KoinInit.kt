package com.example.eval_millot.di

import com.example.eval_millot.data.locations.LocationsRepositoryImpl
import com.example.eval_millot.data.locations.local.InMemoryLocationCacheDataSource
import com.example.eval_millot.data.locations.local.LocationCacheDataSource
import com.example.eval_millot.data.locations.remote.HttpLocationApi
import com.example.eval_millot.data.locations.remote.LocationApi
import com.example.eval_millot.domain.locations.LocationsRepository
import com.example.eval_millot.presentation.locationdetail.LocationDetailViewModel
import com.example.eval_millot.presentation.locationlist.LocationListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

// Demarre Koin avec le wiring partage et les specialisations de plateforme.
fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }

// Regroupe les dependances partagees par Android et Desktop.
val sharedModule: Module = module {
    single<LocationApi> { HttpLocationApi(get()) }
    single<LocationCacheDataSource> { InMemoryLocationCacheDataSource() }
    single<LocationsRepository> {
        LocationsRepositoryImpl(
            locationApi = get(),
            cacheDataSource = get(),
        )
    }
    viewModel { LocationListViewModel(get()) }
    viewModel { LocationDetailViewModel(get()) }
}

// Fournit les dependances qui varient selon la plateforme.
expect val platformModule: Module
