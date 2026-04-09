package com.example.eval_millot.domain.locations

// Représente les données métier affichées sur l'écran de détail.
data class LocationDetail(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentCount: Int,
)
