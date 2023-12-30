package com.example.inzproject.viewmodels

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.inzproject.WeatherForecast.domain.location.LocationTracker
//weatherapp.domain.location.LocationTracker
import com.example.inzproject.domain.location.LocationTracker
import com.example.inzproject.WeatherForecast.domain.repository.WeatherRepository2
import com.example.inzproject.WeatherForecast.domain.util.Resource
import com.example.inzproject.WeatherForecast.presentation.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private var repository: WeatherRepository2,
    private var locationTracker: LocationTracker,
): ViewModel() {
    var textState by   mutableStateOf("")
    var cityName: String = "mylocation"

    //  Function to update parameters after construction

    fun printrepo( )
    {
        println(repository)
    }

  fun settext(text:String){

      textState = text
  }
    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo(context: Context, city: String, selecteddateid: Int) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )

            var isWaiting = true

            if (city == "mylocation") {
         //  if(locationTracker != null){

               try{
              locationTracker.getCurrentLocation()!!.let { location ->
//              var cordinates = getCoordinates(context)
              if(location!= null) {
               //   try {
                      val result = withContext(Dispatchers.IO) {
                          repository!!.getWeatherData(location.latitude, location.longitude)
                      }

                      when (result) {
                          is Resource.Success -> {
                              val geocoder = Geocoder(context, Locale.getDefault())
                              val addresses = geocoder.getFromLocation(
                                  location.latitude,
                                  location.longitude,
                                  1
                              )

                              if (addresses != null) {
                                  cityName = addresses[0].locality
                                  if (cityName != null) {
                                      println("Nazwa miasta na podstawie współrzędnych to: $cityName")
                                  } else {

                                      cityName="Unknown"
                                      println("Nie udało się uzyskać nazwy miasta na podstawie współrzędnych.")




                                  }
                              }

                              state = state.copy(
                                  weatherInfo = result.data,
                                  day = selecteddateid,
                                  city = cityName!!,
                                  isLoading = false,
                                  error = null
                              )
                          }
                          is Resource.Error -> {
                              state = state.copy(
                                  weatherInfo = null,
                                  isLoading = false,
                                  error = result.message
                              )
                              textState = "err"
                          }
                      }
//                  } catch (e: Exception) {
//                      e.printStackTrace()
//                      state = state.copy(
//                          isLoading = false,
//                          error = "An unknown error occurred."
//                      )
//                  }

              }
              else{
                  kotlin.run {
                      state = state.copy(
                          isLoading = false,
                          error = "Couldn't retrieve location. Make sure to grant permission and enable GPS. tak to to"
                      )
                  }
                  textState = "err"
              }

              }}catch (e: Exception) {
                        e.printStackTrace()
                        state = state.copy(
                      weatherInfo = null,
                            isLoading = false,
                            error = "Couldn't retrieve location. Make sure to grant permission and enable GPS nie to to"

                        )
                   textState = "err"
                    }





//           }else{
//
//                kotlin.run {
//                    state = state.copy(
//                        isLoading = false,
//                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
//                    )
//                }}
            }else {
                try {
                    var isWaiting = true
                    state = state.copy(
                        isLoading = true,
                        error = null
                    )

                    val result = withContext(Dispatchers.IO) {
                        val gc = Geocoder(context, Locale.getDefault())
                        val addresses = gc.getFromLocationName(city, 2)

                        if (addresses != null && addresses.isNotEmpty()) {
                            val address = addresses[0]

                            repository!!.getWeatherData(address.latitude, address.longitude)
                        } else {
                            state = state.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = "To miejsce jest nieznane!"
                            )

                            null
                        }
                    }

                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                city = city,
                                day = selecteddateid,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                        else -> {}
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    state = state.copy(
                        isLoading = false,
                        error = "An unknown error occurred."
                    )
                }
            }

            // Operacja sieciowa została zakończona, więc ustaw flagę isWaiting na false.
            isWaiting = false
        }
    }}

fun getCoordinates(context: Context): Pair<Double, Double>? {
    // Sprawdzenie uprawnień do lokalizacji

        // Inicjalizacja LocationManagera
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager

        // Sprawdzenie dostępności usług lokalizacyjnych
        if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
            try {
                // Uzyskanie ostatniej znanej lokalizacji
                val lastKnownLocation =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                lastKnownLocation?.let {
                    // Zwrócenie współrzędnych
                    return Pair(it.latitude, it.longitude)
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }


    // Zwróć null, jeśli nie udało się uzyskać współrzędnych
    return null
}


