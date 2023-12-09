package com.example.inzproject.data.repository

import com.example.inzproject.data.mappers.toWeatherInfo
import com.example.inzproject.data.remote.WeatherApi
import com.example.inzproject.domain.repository.WeatherRepository
import com.example.inzproject.domain.util.Resource
import com.example.inzproject.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}