package com.example.inzproject.PlacesToVisit.Repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GooglePlacesRepository(private val api: CordinatesApi) {
    suspend fun getCoordinatesForPlace(placeName: String, apiKey: String): Coordinates? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPlaceCoordinates(placeName, key = apiKey)

                if (response.isSuccessful) {
                    val candidate = response.body()?.results?.firstOrNull()
                    val location = candidate?.geometry?.location

                    if (location != null) {
                        return@withContext Coordinates(location.lat, location.lng)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@withContext null
        }
    }
}


