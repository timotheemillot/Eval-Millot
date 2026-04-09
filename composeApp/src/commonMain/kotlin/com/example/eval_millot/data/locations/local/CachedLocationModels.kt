package com.example.eval_millot.data.locations.local

// Stocke les données de liste en cache sans exposer de types d'interface ou réseau.
data class CachedLocationSummary(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentCount: Int,
)

// Stocke les données de détail en cache pour une réutilisation ultérieure dans le référentiel.
data class CachedLocationDetail(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentCount: Int,
)
