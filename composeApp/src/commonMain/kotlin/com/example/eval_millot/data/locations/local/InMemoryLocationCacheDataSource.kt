package com.example.eval_millot.data.locations.local

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// Garde les locations récupérées en mémoire pour la session courante.
class InMemoryLocationCacheDataSource : LocationCacheDataSource {
    private val mutex = Mutex()
    private var cachedLocations: List<CachedLocationSummary> = emptyList()
    private val cachedDetails = mutableMapOf<Int, CachedLocationDetail>()

// Renvoie l'instantané de liste mis en cache lorsqu'il existe.
    override suspend fun getLocations(): List<CachedLocationSummary> = mutex.withLock {
        cachedLocations
    }

// Remplace la liste en cache par les dernières valeurs récupérées.
    override suspend fun saveLocations(locations: List<CachedLocationSummary>) {
        mutex.withLock {
            cachedLocations = locations
        }
    }

// Recherche un détail en cache par identifiant de location.
    override suspend fun getLocationDetail(id: Int): CachedLocationDetail? = mutex.withLock {
        cachedDetails[id]
    }

// Stocke une entrée de détail pour les futures requêtes de détail.
    override suspend fun saveLocationDetail(detail: CachedLocationDetail) {
        mutex.withLock {
            cachedDetails[detail.id] = detail
        }
    }
}
