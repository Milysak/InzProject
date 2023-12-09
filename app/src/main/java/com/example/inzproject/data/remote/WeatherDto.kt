package com.example.inzproject.data.remote

import com.example.inzproject.data.remote.WeatherDataDto
import com.squareup.moshi.Json

data class WeatherDto(
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDto
)
