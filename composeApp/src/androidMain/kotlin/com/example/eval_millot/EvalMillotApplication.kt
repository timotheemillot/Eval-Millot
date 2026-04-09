package com.example.eval_millot

import android.app.Application
import com.example.eval_millot.di.initKoin
import org.koin.android.ext.koin.androidContext

// Initialise Koin une seule fois pour le cycle de vie Android.
class EvalMillotApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@EvalMillotApplication)
        }
    }
}
