package com.example.inzproject.PlacesToVisit.Repository

import com.example.inzproject.PlacesToVisit.PlaceClass
import com.example.inzproject.PlacesToVisit.PlacesResponse


//klasa przechowująca to co jest wyświetlane
data class PlaceState(
    val PlaceInfo: PlacesResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val places: List<PlaceClass> = emptyList(),
    val favoritePlacesIds: List<String> = emptyList(),
)
