package com.example.eval_millot.navigation

// Décrit quelle destination partagée l'application doit afficher.
sealed interface LocationDestination {
// Affiche la vue liste sans élément de détail sélectionné.
    data object List : LocationDestination

// Affiche la vue détail pour un identifiant de location sélectionné.
    data class Detail(val locationId: Int) : LocationDestination
}
