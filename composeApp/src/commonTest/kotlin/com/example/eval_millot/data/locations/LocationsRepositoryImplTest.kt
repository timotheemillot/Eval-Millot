package com.example.eval_millot.data.locations

import com.example.eval_millot.data.locations.local.CachedLocationDetail
import com.example.eval_millot.data.locations.local.CachedLocationSummary
import com.example.eval_millot.data.locations.local.LocationCacheDataSource
import com.example.eval_millot.data.locations.remote.LocationApi
import com.example.eval_millot.data.locations.remote.RemoteLocationDto
import com.example.eval_millot.domain.locations.LocationDetail
import com.example.eval_millot.domain.locations.LocationSummary
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

// Couvre le comportement de cache et de mapping du repository.
class LocationsRepositoryImplTest {
    @Test
    fun get_locations_uses_cached_values_before_remote() = runTest {
        val locationApi = FakeLocationApi(
            locations = listOf(
                RemoteLocationDto(
                    id = 13,
                    name = "Should Not Load",
                    type = "Planet",
                    dimension = "Blocked",
                    residents = listOf("one"),
                ),
            ),
        )
        val cacheDataSource = FakeCacheDataSource(
            cachedLocations = listOf(
                CachedLocationSummary(
                    id = 42,
                    name = "Citadel of Ricks",
                    type = "Space station",
                    dimension = "Unknown",
                    residentCount = 8,
                ),
            ),
        )
        val repository = LocationsRepositoryImpl(
            locationApi = locationApi,
            cacheDataSource = cacheDataSource,
        )

        val locations = repository.getLocations()

        assertEquals(
            expected = listOf(
                LocationSummary(
                    id = 42,
                    name = "Citadel of Ricks",
                    type = "Space station",
                    dimension = "Unknown",
                    residentCount = 8,
                ),
            ),
            actual = locations,
        )
        assertEquals(0, locationApi.getLocationsCalls)
    }

    @Test
    fun get_locations_maps_remote_dtos_to_domain_and_caches_summaries() = runTest {
        val locationApi = FakeLocationApi(
            locations = listOf(
                RemoteLocationDto(
                    id = 1,
                    name = "Earth",
                    type = "Planet",
                    dimension = "Dimension C-137",
                    residents = listOf("rick", "morty"),
                ),
            ),
        )
        val cacheDataSource = FakeCacheDataSource()
        val repository = LocationsRepositoryImpl(
            locationApi = locationApi,
            cacheDataSource = cacheDataSource,
        )

        val locations = repository.getLocations()

        assertEquals(
            expected = listOf(
                LocationSummary(
                    id = 1,
                    name = "Earth",
                    type = "Planet",
                    dimension = "Dimension C-137",
                    residentCount = 2,
                ),
            ),
            actual = locations,
        )
        assertEquals(
            expected = listOf(
                CachedLocationSummary(
                    id = 1,
                    name = "Earth",
                    type = "Planet",
                    dimension = "Dimension C-137",
                    residentCount = 2,
                ),
            ),
            actual = cacheDataSource.savedLocations,
        )
        assertEquals(1, locationApi.getLocationsCalls)
    }

    @Test
    fun get_location_detail_maps_remote_dto_to_domain_and_caches_detail() = runTest {
        val locationApi = FakeLocationApi(
            detail = RemoteLocationDto(
                id = 7,
                name = "Gazorpazorp",
                type = "Planet",
                dimension = "Unknown",
                residents = listOf("one", "two", "three"),
            ),
        )
        val cacheDataSource = FakeCacheDataSource()
        val repository = LocationsRepositoryImpl(
            locationApi = locationApi,
            cacheDataSource = cacheDataSource,
        )

        val detail = repository.getLocationDetail(id = 7)

        assertEquals(
            expected = LocationDetail(
                id = 7,
                name = "Gazorpazorp",
                type = "Planet",
                dimension = "Unknown",
                residentCount = 3,
            ),
            actual = detail,
        )
        assertEquals(
            expected = CachedLocationDetail(
                id = 7,
                name = "Gazorpazorp",
                type = "Planet",
                dimension = "Unknown",
                residentCount = 3,
            ),
            actual = cacheDataSource.savedDetail,
        )
        assertEquals(1, locationApi.getLocationDetailCalls)
    }

    @Test
    fun get_location_detail_uses_cached_detail_before_remote() = runTest {
        val locationApi = FakeLocationApi(
            detail = RemoteLocationDto(
                id = 99,
                name = "Should Not Load",
                type = "Cluster",
                dimension = "Blocked",
                residents = emptyList(),
            ),
        )
        val cacheDataSource = FakeCacheDataSource(
            cachedDetail = CachedLocationDetail(
                id = 99,
                name = "Cached Earth",
                type = "Planet",
                dimension = "Replacement Dimension",
                residentCount = 4,
            ),
        )
        val repository = LocationsRepositoryImpl(
            locationApi = locationApi,
            cacheDataSource = cacheDataSource,
        )

        val detail = repository.getLocationDetail(id = 99)

        assertEquals(
            expected = LocationDetail(
                id = 99,
                name = "Cached Earth",
                type = "Planet",
                dimension = "Replacement Dimension",
                residentCount = 4,
            ),
            actual = detail,
        )
        assertEquals(0, locationApi.getLocationDetailCalls)
    }

    private class FakeLocationApi(
        private val locations: List<RemoteLocationDto> = emptyList(),
        private val detail: RemoteLocationDto = RemoteLocationDto(
            id = 0,
            name = "",
            type = "",
            dimension = "",
            residents = emptyList(),
        ),
    ) : LocationApi {
        var getLocationsCalls: Int = 0
            private set
        var getLocationDetailCalls: Int = 0
            private set

        override suspend fun getLocations(): List<RemoteLocationDto> {
            getLocationsCalls += 1
            return locations
        }

        override suspend fun getLocationDetail(id: Int): RemoteLocationDto {
            getLocationDetailCalls += 1
            return detail
        }
    }

    private class FakeCacheDataSource(
        private val cachedLocations: List<CachedLocationSummary> = emptyList(),
        private val cachedDetail: CachedLocationDetail? = null,
    ) : LocationCacheDataSource {
        var savedLocations: List<CachedLocationSummary> = emptyList()
            private set
        var savedDetail: CachedLocationDetail? = null
            private set

        override suspend fun getLocations(): List<CachedLocationSummary> = cachedLocations

        override suspend fun saveLocations(locations: List<CachedLocationSummary>) {
            savedLocations = locations
        }

        override suspend fun getLocationDetail(id: Int): CachedLocationDetail? = cachedDetail

        override suspend fun saveLocationDetail(detail: CachedLocationDetail) {
            savedDetail = detail
        }
    }
}
