package com.example.inzproject.PlacesToVisit



// PlaceApiManager.kt


import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


data class PlaceApiResponse(val results: List<Place>)

class PlaceApiManager {

    private val client = HttpClient(Android) {
        install(JsonFeature)
    }

    suspend fun makeApiRequest(location: String, radius: Int, type: String): PlaceApiResponse? {
        return try {
            val url = buildApiUrl(location, radius, type)

            val response = withContext(Dispatchers.IO) {
                client.get<PlaceApiResponse>(url.toString())
            }

            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun buildApiUrl(location: String, radius: Int, type: String): String {
        return URLBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json").apply {
            parameters.apply {
                append("location", location)
                append("radius", radius.toString())
                append("type", type)
                // Dodaj swój klucz API (w tym przypadku założyłem, że jest to zmienna o nazwie apiKey)
                append("key", apiKey)
            }
        }.buildString()
    }

    // Dodaj swój klucz API tutaj
    private val apiKey = "TwójKluczApi"
}