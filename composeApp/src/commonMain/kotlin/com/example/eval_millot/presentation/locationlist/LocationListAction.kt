package com.example.eval_millot.presentation.locationlist

// Liste les intentions qui peuvent modifier l'état de la liste de locations.
sealed interface LocationListAction {
// Demande le chargement initial de la liste.
    data object LoadRequested : LocationListAction

// Demande à nouveau la liste après une erreur.
    data object RetryClicked : LocationListAction

// Indique quelle location l'utilisateur a sélectionnée dans la liste.
    data class LocationClicked(val locationId: Int) : LocationListAction
}
