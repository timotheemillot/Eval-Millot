package com.example.eval_millot.presentation.locationdetail

// Contient seulement les données nécessaires pour rendre l'écran détail.
data class LocationDetailUiState(
    val isLoading: Boolean = false,
    val detail: LocationDetailContentUiModel? = null,
    val errorMessage: String? = null,
    val placeholderMessage: String? = "Selectionnez une location pour voir son detail.",
)

// Stocke les libellés de détail préformatés pour la couche d'interface.
data class LocationDetailContentUiModel(
    val title: String,
    val typeLabel: String,
    val dimensionLabel: String,
    val residentsLabel: String,
)
