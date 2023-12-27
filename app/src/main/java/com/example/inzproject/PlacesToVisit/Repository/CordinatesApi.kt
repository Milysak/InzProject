package com.example.inzproject.PlacesToVisit.Repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CordinatesApi {
    // Uzyskiwanie współrzędnych na podstawie nazwy miejsca
    @GET("/maps/api/geocode/json")
    suspend fun getPlaceCoordinates(
        @Query("address") address: String,
        @Query("inputtype") inputType: String = "textquery",
        @Query("key") key: String
    ): Response<GooglePlacesApiResponse>
}