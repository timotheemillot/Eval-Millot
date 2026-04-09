package com.example.eval_millot.data.locations.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

// Charge les donnees du domaine locations depuis l'API Rick and Morty.
class HttpLocationApi(
    private val httpClient: HttpClient,
) : LocationApi {
    override suspend fun getLocations(): List<RemoteLocationDto> {
        val response = httpClient.get("https://rickandmortyapi.com/api/location")
        if (!response.status.isSuccess()) {
            throw IllegalStateException(
                "HTTP ${response.status.value} lors du chargement des locations.",
            )
        }

        return response
            .bodyAsText()
            .let(::parseRemoteLocations)
    }

    override suspend fun getLocationDetail(id: Int): RemoteLocationDto {
        val response = httpClient.get("https://rickandmortyapi.com/api/location/$id")
        if (!response.status.isSuccess()) {
            throw IllegalStateException(
                "HTTP ${response.status.value} lors du chargement du detail de la location $id.",
            )
        }

        return response
            .bodyAsText()
            .let(::parseRemoteLocation)
    }
}
