package com.example.eval_millot.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Possède la destination courante liste/détail pour le code d'interface partagé.
class LocationNavigator {
    private val _destination = MutableStateFlow<LocationDestination>(LocationDestination.List)

    val destination: StateFlow<LocationDestination> = _destination.asStateFlow()

// Ramène l'application à la destination liste.
    fun openList() {
        _destination.value = LocationDestination.List
    }

// Ouvre la destination détail pour la location sélectionnée.
    fun openDetail(locationId: Int) {
        _destination.value = LocationDestination.Detail(locationId)
    }
}
