package com.example.inzproject.WeatherForecast.domain.repository

import com.example.inzproject.WeatherForecast.domain.util.Resource
import com.example.inzproject.WeatherForecast.domain.weather.WeatherInfo

interface WeatherRepository2 {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}