package com.example.eval_millot.data.locations.remote

// Reflète les champs distants nécessaires aux mappings du référentiel.
data class RemoteLocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
)
