package com.example.eval_millot

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.eval_millot.di.initKoin
import org.koin.core.context.stopKoin

// Demarre Koin puis la fenetre Desktop partagee.
fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "EvalMillot",
        ) {
            App(layoutMode = AppLayoutMode.Desktop)
        }
    }

    stopKoin()
}
