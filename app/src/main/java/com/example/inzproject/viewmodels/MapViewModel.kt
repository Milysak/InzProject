package com.example.inzproject.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inzproject.R
import com.example.inzproject.WeatherForecast.domain.repository.WeatherRepository2
import com.example.inzproject.data.dataclasses.MyMarker
import com.example.inzproject.domain.location.LocationTracker
import com.example.inzproject.domain.repository.WeatherRepository
import com.example.inzproject.domain.weather.WeatherData
import com.example.inzproject.domain.weather.WeatherType
import com.example.inzproject.mapstyles.DarkMapStyle
import com.example.inzproject.mapstyles.LightMapStyle
import com.example.inzproject.states.MapState
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.clustering.ClusterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private var repository: WeatherRepository,
): ViewModel() {
    var state by mutableStateOf(MapState())

    var location by mutableStateOf("")

    var latitude by mutableStateOf(50.262606656386104)

    var longitude by mutableStateOf(19.03967912803772)

    var zoom by mutableStateOf(12f)

    var markers = mutableListOf<MyMarker>()

    var added by mutableStateOf(false)

    private val darkMapTheme = MapStyleOptions(DarkMapStyle.json)

    private val lightMapTheme = MapStyleOptions(LightMapStyle.json)

    suspend fun getWeatherIcon(item: ClusterItem) : WeatherData? =
        repository.getWeatherData(item.position.latitude, item.position.longitude)
            .data?.currentWeatherData

    fun setDarkMapTheme() {
        state = state.copy(
            properties = state.properties.copy(
                mapStyleOptions = darkMapTheme
            )
        )
    }

    fun setLightMapTheme() {
        state = state.copy(
            properties = state.properties.copy(
                mapStyleOptions = lightMapTheme
            )
        )
    }
}