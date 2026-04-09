package com.example.eval_millot.data.locations

import com.example.eval_millot.data.locations.local.CachedLocationDetail
import com.example.eval_millot.data.locations.local.CachedLocationSummary
import com.example.eval_millot.data.locations.local.LocationCacheDataSource
import com.example.eval_millot.data.locations.remote.LocationApi
import com.example.eval_millot.data.locations.remote.RemoteLocationDto
import com.example.eval_millot.domain.locations.LocationDetail
import com.example.eval_millot.domain.locations.LocationSummary
import com.example.eval_millot.domain.locations.LocationsRepository

// Combine l'API du domaine locations et le cache derriere le contrat metier.
class LocationsRepositoryImpl(
    private val locationApi: LocationApi,
    private val cacheDataSource: LocationCacheDataSource,
) : LocationsRepository {
    override suspend fun getLocations(): List<LocationSummary> {
        val cachedLocations = cacheDataSource.getLocations()
        if (cachedLocations.isNotEmpty()) {
            return cachedLocations.map(::cachedSummaryToDomain)
        }

        val remoteLocations = locationApi.getLocations()
        cacheDataSource.saveLocations(
            remoteLocations.map(::remoteSummaryToCache),
        )
        return remoteLocations.map(::remoteSummaryToDomain)
    }

    override suspend fun getLocationDetail(id: Int): LocationDetail {
        val cachedDetail = cacheDataSource.getLocationDetail(id)
        if (cachedDetail != null) {
            return cachedDetailToDomain(cachedDetail)
        }

        val remoteDetail = locationApi.getLocationDetail(id)
        cacheDataSource.saveLocationDetail(remoteDetailToCache(remoteDetail))
        return remoteDetailToDomain(remoteDetail)
    }

    private fun cachedSummaryToDomain(cachedLocation: CachedLocationSummary): LocationSummary {
        return LocationSummary(
            id = cachedLocation.id,
            name = cachedLocation.name,
            type = cachedLocation.type,
            dimension = cachedLocation.dimension,
            residentCount = cachedLocation.residentCount,
        )
    }

    private fun remoteSummaryToCache(remoteLocation: RemoteLocationDto): CachedLocationSummary {
        return CachedLocationSummary(
            id = remoteLocation.id,
            name = remoteLocation.name,
            type = remoteLocation.type,
            dimension = remoteLocation.dimension,
            residentCount = remoteLocation.residents.size,
        )
    }

    private fun remoteSummaryToDomain(remoteLocation: RemoteLocationDto): LocationSummary {
        return LocationSummary(
            id = remoteLocation.id,
            name = remoteLocation.name,
            type = remoteLocation.type,
            dimension = remoteLocation.dimension,
            residentCount = remoteLocation.residents.size,
        )
    }

    private fun cachedDetailToDomain(cachedDetail: CachedLocationDetail): LocationDetail {
        return LocationDetail(
            id = cachedDetail.id,
            name = cachedDetail.name,
            type = cachedDetail.type,
            dimension = cachedDetail.dimension,
            residentCount = cachedDetail.residentCount,
        )
    }

    private fun remoteDetailToCache(remoteDetail: RemoteLocationDto): CachedLocationDetail {
        return CachedLocationDetail(
            id = remoteDetail.id,
            name = remoteDetail.name,
            type = remoteDetail.type,
            dimension = remoteDetail.dimension,
            residentCount = remoteDetail.residents.size,
        )
    }

    private fun remoteDetailToDomain(remoteDetail: RemoteLocationDto): LocationDetail {
        return LocationDetail(
            id = remoteDetail.id,
            name = remoteDetail.name,
            type = remoteDetail.type,
            dimension = remoteDetail.dimension,
            residentCount = remoteDetail.residents.size,
        )
    }
}
