package com.example.eval_millot.data.locations.remote

// Definit l'API du domaine locations utilisee par le repository.
interface LocationApi {
    suspend fun getLocations(): List<RemoteLocationDto>

    suspend fun getLocationDetail(id: Int): RemoteLocationDto
}
