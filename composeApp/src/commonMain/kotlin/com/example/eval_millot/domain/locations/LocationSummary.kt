package com.example.eval_millot.domain.locations

// Représente les données métier affichées dans la liste des locations.
data class LocationSummary(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentCount: Int,
)
