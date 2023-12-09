package com.example.inzproject.domain.repository

import com.example.inzproject.domain.util.Resource
import com.example.inzproject.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}