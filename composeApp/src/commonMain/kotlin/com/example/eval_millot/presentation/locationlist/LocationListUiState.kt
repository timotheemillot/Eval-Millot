package com.example.eval_millot.presentation.locationlist

// Contient seulement les données nécessaires pour rendre la liste des locations.
data class LocationListUiState(
    val isLoading: Boolean = false,
    val locations: List<LocationListItemUiModel> = emptyList(),
    val errorMessage: String? = null,
)

// Stocke le texte préformaté pour une carte de location.
data class LocationListItemUiModel(
    val id: Int,
    val title: String,
    val typeLabel: String,
    val dimensionLabel: String,
    val residentsLabel: String,
)
