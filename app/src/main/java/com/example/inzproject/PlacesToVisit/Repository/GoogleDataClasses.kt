package com.example.inzproject.PlacesToVisit.Repository


data class GooglePlacesApiResponse(
    val results: List<GooglePlaceCandidate>
)

data class GooglePlaceCandidate(
    val geometry: GoogleGeometry
)

data class GoogleGeometry(
    val location: GoogleLocation
)

data class GoogleLocation(
    val lat: Double,
    val lng: Double
)
