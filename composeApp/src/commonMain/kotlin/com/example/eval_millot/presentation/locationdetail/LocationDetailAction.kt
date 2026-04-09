package com.example.eval_millot.presentation.locationdetail

// Liste les intentions qui peuvent modifier l'état de l'écran détail.
sealed interface LocationDetailAction {
// Demande les données de détail pour une nouvelle location sélectionnée.
    data class LoadRequested(val locationId: Int) : LocationDetailAction

// Relance la dernière requête de détail après un échec.
    data object RetryClicked : LocationDetailAction

// Revient à l'écran liste sur mobile.
    data object BackClicked : LocationDetailAction

// Efface le détail courant quand aucune sélection ne doit rester visible.
    data object ClearRequested : LocationDetailAction
}
