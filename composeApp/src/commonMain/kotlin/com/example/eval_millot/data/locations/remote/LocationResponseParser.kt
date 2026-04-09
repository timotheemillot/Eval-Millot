package com.example.eval_millot.data.locations.remote

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private val parser = Json {
    ignoreUnknownKeys = true
}

// Extrait le tableau de résultats de l'API en liste de DTO.
fun parseRemoteLocations(payload: String): List<RemoteLocationDto> {
    val rootObject = parser.parseToJsonElement(payload).jsonObject
    val resultArray = rootObject["results"]?.jsonArray.orEmpty()

    return resultArray.map { element -> element.jsonObject.toRemoteLocationDto() }
}

// Analyse une charge utile de location unique en un DTO.
fun parseRemoteLocation(payload: String): RemoteLocationDto {
    return parser.parseToJsonElement(payload).jsonObject.toRemoteLocationDto()
}

// Lit les champs nécessaires à l'application depuis un objet location de l'API.
private fun kotlinx.serialization.json.JsonObject.toRemoteLocationDto(): RemoteLocationDto {
    return RemoteLocationDto(
        id = getValue("id").jsonPrimitive.int,
        name = getValue("name").jsonPrimitive.content,
        type = getValue("type").jsonPrimitive.content,
        dimension = getValue("dimension").jsonPrimitive.content,
        residents = getValue("residents").jsonArray.map { resident ->
            resident.jsonPrimitive.content
        },
    )
}
