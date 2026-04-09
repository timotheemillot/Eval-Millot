package com.example.eval_millot.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

// Utilise le moteur HTTP Android requis par le code réseau partagé.
actual fun createPlatformHttpClient(): HttpClient = HttpClient(OkHttp)
