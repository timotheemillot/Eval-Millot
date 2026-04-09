package com.example.eval_millot.presentation.locationdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eval_millot.domain.locations.LocationDetail
import com.example.eval_millot.domain.locations.LocationsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Coordonne les requetes de detail et expose un etat d'interface pret a rendre.
class LocationDetailViewModel(
    private val locationsRepository: LocationsRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationDetailUiState())
    private val detailRequests = MutableStateFlow(LocationDetailRequest())

    val uiState: StateFlow<LocationDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(coroutineDispatcher) {
            detailRequests.collectLatest { request ->
                val locationId = request.locationId
                if (locationId == null) {
                    _uiState.value = LocationDetailUiState()
                    return@collectLatest
                }

                _uiState.value = LocationDetailUiState(
                    isLoading = true,
                    placeholderMessage = null,
                )

                runCatching { locationsRepository.getLocationDetail(locationId) }
                    .onSuccess { detail ->
                        if (detailRequests.value == request) {
                            _uiState.value = LocationDetailUiState(
                                isLoading = false,
                                detail = detail.toUiModel(),
                                placeholderMessage = null,
                            )
                        }
                    }
                    .onFailure { throwable ->
                        if (throwable is CancellationException) {
                            throw throwable
                        }

                        if (detailRequests.value == request) {
                            _uiState.value = LocationDetailUiState(
                                isLoading = false,
                                errorMessage = throwable.message
                                    ?: "Impossible de charger ce detail.",
                                placeholderMessage = null,
                            )
                        }
                    }
            }
        }
    }

    // Convertit les actions d'interface en demandes de chargement, relance ou effacement.
    fun onAction(action: LocationDetailAction) {
        when (action) {
            is LocationDetailAction.LoadRequested -> detailRequests.value =
                detailRequests.value.load(action.locationId)

            LocationDetailAction.RetryClicked -> {
                val currentLocationId = detailRequests.value.locationId ?: return
                detailRequests.value = detailRequests.value.load(currentLocationId)
            }

            LocationDetailAction.ClearRequested -> detailRequests.value = detailRequests.value.clear()
            LocationDetailAction.BackClicked -> Unit
        }
    }
}

// Suit la requete active pour pouvoir ignorer les reponses tardives en securite.
private data class LocationDetailRequest(
    val locationId: Int? = null,
    val version: Long = 0L,
) {
    fun load(locationId: Int): LocationDetailRequest {
        return copy(locationId = locationId, version = version + 1)
    }

    fun clear(): LocationDetailRequest {
        return copy(locationId = null, version = version + 1)
    }
}

// Formate les donnees de detail du domaine en libelles prets pour l'affichage.
private fun LocationDetail.toUiModel(): LocationDetailContentUiModel {
    val residentsLabel = if (residentCount == 0) {
        "Residents : aucun resident connu"
    } else {
        "Residents : $residentCount residents connus"
    }

    return LocationDetailContentUiModel(
        title = name,
        typeLabel = "Type : $type",
        dimensionLabel = "Dimension : $dimension",
        residentsLabel = residentsLabel,
    )
}
