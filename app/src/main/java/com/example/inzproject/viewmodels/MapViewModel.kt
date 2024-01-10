package com.example.inzproject.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.inzproject.data.mapROOM.database.SpecialPlace
import com.example.inzproject.data.mapROOM.repository.SpecialPlaceRepository
import com.example.inzproject.domain.location.LocationTracker
import com.example.inzproject.domain.repository.WeatherRepository
import com.example.inzproject.domain.util.Resource
import com.example.inzproject.domain.weather.WeatherData
import com.example.inzproject.mapstyles.DarkMapStyle
import com.example.inzproject.mapstyles.LightMapStyle
import com.example.inzproject.states.MapState
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private var repository: WeatherRepository,
    private var specialPlaceRepository: SpecialPlaceRepository,
    private var locationTracker: LocationTracker,
    ): ViewModel() {
    var state by mutableStateOf(MapState())

    var cameraZoom by mutableStateOf(0f)

    var location by mutableStateOf("")

    var selectedSpecialPlace: SpecialPlace? by mutableStateOf(null)

    var coords: LatLng? by mutableStateOf(null)

    var latitude by mutableStateOf(50.262606656386104)

    var longitude by mutableStateOf(19.03967912803772)

    var newPlaceLocation: LatLng? by mutableStateOf(null)

    var newPlaceVisibility by mutableStateOf(false)

    var zoom by mutableStateOf(12f)

    var markers = mutableListOf<Marker?>()

    var specialMarkers = mutableListOf<Marker?>()

    var specialMarkersChanged by mutableStateOf(false)

    var added by mutableStateOf(false)

    val specialPlacesList: LiveData<List<SpecialPlace>> = specialPlaceRepository.allSpecialPlaces.asLiveData()

    var specialPlaces = mutableListOf<SpecialPlace>()

    private val darkMapTheme = MapStyleOptions(DarkMapStyle.json)

    private val lightMapTheme = MapStyleOptions(LightMapStyle.json)

    var weatherData: WeatherData? by mutableStateOf(null)
        private set

    fun getWeather(
        latitude: Double,
        longitude: Double,
        onError: (WeatherData?) -> Unit,
        onSuccess: (WeatherData?) -> Unit
        ) {
        viewModelScope.launch {

            val result = withContext(Dispatchers.IO) {
                repository.getWeatherData(latitude, longitude)
            }

            when (
                result
            ) {
                is Resource.Success -> {
                    onSuccess(result.data?.currentWeatherData)
                }
                is Resource.Error -> {
                    onError(result.data?.currentWeatherData)
                }
            }

        }
    }

    fun getWeatherIcon(
        latitude: Double,
        longitude: Double
    ) : Int? = runBlocking {
            repository.getWeatherData(latitude, longitude).data?.currentWeatherData?.weatherType?.iconRes
        }


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

    /*fun getAllSpecialPlaces() {
        specialPlaceRepository.getAllSpecialPlaces()
    }*/

    fun addSpecialPlace(specialPlace: SpecialPlace) = viewModelScope.launch {
        specialPlaceRepository.addSpecialPlace(specialPlace)
    }

    fun deleteSpecialPlace(specialPlace: SpecialPlace) = viewModelScope.launch {
        specialPlaceRepository.deleteSpecialPlace(specialPlace)
    }

    fun setCurrentLocation() {
        viewModelScope.launch {
            try {
                locationTracker.getCurrentLocation()!!.let { location ->
                    latitude = location.latitude
                    longitude = location.longitude
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Bez zmian
            }
        }
    }

}