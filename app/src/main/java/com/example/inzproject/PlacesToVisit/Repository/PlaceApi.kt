package com.example.inzproject.PlacesToVisit.Repository

import com.example.inzproject.PlacesToVisit.PlaceClass
import com.example.inzproject.PlacesToVisit.PlacesResponse
import com.example.inzproject.WeatherForecast.data.remote.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceApi {

    @GET("maps/api/place/nearbysearch/json?")
    suspend fun getPlaces(
        @Query("keyword") keyword: String? ,
        @Query("location") loc: String,
        @Query("radius") rad: String,
        @Query("type") type: String,
        @Query("key") key: String,
    ): Response<PlacesResponse>
}