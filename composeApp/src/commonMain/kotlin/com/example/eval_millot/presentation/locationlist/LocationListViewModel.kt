package com.example.eval_millot.presentation.locationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eval_millot.domain.locations.LocationSummary
import com.example.eval_millot.domain.locations.LocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Coordonne le chargement de la liste et expose un etat d'interface pret a rendre.
class LocationListViewModel(
    private val locationsRepository: LocationsRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LocationListUiState(isLoading = true))

    val uiState: StateFlow<LocationListUiState> = _uiState.asStateFlow()

    // Reagit aux intentions de chargement et de relance depuis l'ecran liste.
    fun onAction(action: LocationListAction) {
        when (action) {
            LocationListAction.LoadRequested,
            LocationListAction.RetryClicked -> loadLocations()

            is LocationListAction.LocationClicked -> Unit
        }
    }

    // Actualise l'etat de la liste a partir du repository partage.
    private fun loadLocations() {
        viewModelScope.launch(coroutineDispatcher) {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            runCatching { locationsRepository.getLocations() }
                .onSuccess { locations ->
                    _uiState.value = LocationListUiState(
                        isLoading = false,
                        locations = locations.map(LocationSummary::toUiModel),
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = LocationListUiState(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Impossible de charger les locations.",
                    )
                }
        }
    }
}

// Formate les resumes du domaine en libelles prets pour les cartes de la liste.
private fun LocationSummary.toUiModel(): LocationListItemUiModel {
    return LocationListItemUiModel(
        id = id,
        title = name,
        typeLabel = "Type : $type",
        dimensionLabel = "Dimension : $dimension",
        residentsLabel = if (residentCount == 0) {
            "Aucun resident connu"
        } else {
            "$residentCount residents connus"
        },
    )
}
