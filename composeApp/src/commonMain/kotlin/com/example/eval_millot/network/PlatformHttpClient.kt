package com.example.eval_millot.network

import io.ktor.client.HttpClient

// Crée le client HTTP de plateforme utilisé par les sources distantes partagées.
expect fun createPlatformHttpClient(): HttpClient
