package com.example.eval_millot.di

import com.example.eval_millot.network.createPlatformHttpClient
import com.example.eval_millot.platform.LocationClickSoundManager
import com.example.eval_millot.platform.createLocationClickSoundManager
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClient> { createPlatformHttpClient() }
    single<LocationClickSoundManager> {
        androidContext().createLocationClickSoundManager()
    }
}
