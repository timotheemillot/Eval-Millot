package com.example.eval_millot.data.locations.local

// Définit le contrat de cache utilisé par la stratégie du référentiel.
interface LocationCacheDataSource {
// Renvoie les éléments de liste mis en cache lorsque le cache est rempli.
    suspend fun getLocations(): List<CachedLocationSummary>

// Persiste les éléments de liste pour les lectures ultérieures avec priorité au cache.
    suspend fun saveLocations(locations: List<CachedLocationSummary>)

// Renvoie le détail mis en cache pour une location quand il est disponible.
    suspend fun getLocationDetail(id: Int): CachedLocationDetail?

// Persiste un détail pour une réutilisation ultérieure.
    suspend fun saveLocationDetail(detail: CachedLocationDetail)
}
