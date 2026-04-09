package com.example.eval_millot.presentation.locationdetail

import com.example.eval_millot.domain.locations.LocationDetail
import com.example.eval_millot.domain.locations.LocationSummary
import com.example.eval_millot.domain.locations.LocationsRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
// Verifie que l'etat de detail reste coherent malgre les courses asynchrones.
class LocationDetailViewModelTest {

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    // Verifie que la selection la plus recente garde la main sur l'etat de detail.
    fun latest_selection_wins_when_requests_finish_out_of_order() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val repository = FakeLocationsRepository()
        val viewModel = LocationDetailViewModel(
            locationsRepository = repository,
            coroutineDispatcher = StandardTestDispatcher(testScheduler),
        )

        viewModel.onAction(LocationDetailAction.LoadRequested(locationId = 1))
        runCurrent()

        viewModel.onAction(LocationDetailAction.LoadRequested(locationId = 2))
        runCurrent()

        repository.completeDetail(id = 1, detail = detail(id = 1, name = "Earth"))
        runCurrent()

        assertTrue(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.detail)

        repository.completeDetail(id = 2, detail = detail(id = 2, name = "Mars"))
        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("Mars", viewModel.uiState.value.detail?.title)
    }

    @Test
    // Verifie que l'effacement du detail empeche les reponses tardives de le restaurer.
    fun clear_requested_prevents_a_late_response_from_restoring_the_detail() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val repository = FakeLocationsRepository()
        val viewModel = LocationDetailViewModel(
            locationsRepository = repository,
            coroutineDispatcher = StandardTestDispatcher(testScheduler),
        )

        viewModel.onAction(LocationDetailAction.LoadRequested(locationId = 1))
        runCurrent()

        viewModel.onAction(LocationDetailAction.ClearRequested)
        runCurrent()

        repository.completeDetail(id = 1, detail = detail(id = 1, name = "Earth"))
        advanceUntilIdle()

        assertEquals(LocationDetailUiState(), viewModel.uiState.value)
    }

    // Retarde les reponses de detail pour permettre aux tests de controler l'ordre de fin.
    private class FakeLocationsRepository : LocationsRepository {
        private val detailRequests = mutableMapOf<Int, CompletableDeferred<LocationDetail>>()

        override suspend fun getLocations(): List<LocationSummary> = emptyList()

        override suspend fun getLocationDetail(id: Int): LocationDetail {
            return detailRequests.getOrPut(id) { CompletableDeferred() }.await()
        }

        fun completeDetail(id: Int, detail: LocationDetail) {
            detailRequests.getOrPut(id) { CompletableDeferred() }.complete(detail)
        }
    }
}

private fun detail(id: Int, name: String): LocationDetail {
    return LocationDetail(
        id = id,
        name = name,
        type = "Planet",
        dimension = "Dimension C-137",
        residentCount = 12,
    )
}
