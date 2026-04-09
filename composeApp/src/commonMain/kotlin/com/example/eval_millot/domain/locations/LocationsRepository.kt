package com.example.eval_millot.domain.locations

// Expose les cas d'usage des locations nécessaires à la couche de présentation.
interface LocationsRepository {
// Renvoie les données de liste utilisées par l'écran d'aperçu des locations.
    suspend fun getLocations(): List<LocationSummary>

// Renvoie les données de détail pour une location sélectionnée.
    suspend fun getLocationDetail(id: Int): LocationDetail
}
