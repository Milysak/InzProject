package com.example.inzproject.WeatherForecast.data.repository

import com.example.inzproject.WeatherForecast.data.mappers.toWeatherInfo
//import com.example.inzproject.WeatherForecast.data.remote.WeatherApi
import com.example.inzproject.WeatherForecast.data.remote.WeatherApi2
import com.example.inzproject.WeatherForecast.domain.repository.WeatherRepository2
import com.example.inzproject.WeatherForecast.domain.util.Resource
import com.example.inzproject.WeatherForecast.domain.weather.WeatherInfo
import com.example.inzproject.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi2
): WeatherRepository2 {

    override suspend fun getWeatherData(latitude: Double, longtitude: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = latitude,
                    long = longtitude
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}