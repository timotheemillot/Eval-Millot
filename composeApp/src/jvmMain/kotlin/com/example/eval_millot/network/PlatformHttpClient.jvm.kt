package com.example.eval_millot.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java

// Utilise le moteur HTTP pour ordinateur requis par le code réseau partagé.
actual fun createPlatformHttpClient(): HttpClient = HttpClient(Java)
