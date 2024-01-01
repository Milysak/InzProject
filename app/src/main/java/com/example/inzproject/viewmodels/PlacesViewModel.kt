package com.example.inzproject.viewmodels

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ZoomOutMap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inzproject.PlacesToVisit.*
import com.example.inzproject.PlacesToVisit.ROOM.PlaceDao
import com.example.inzproject.PlacesToVisit.Repository.*
import com.example.inzproject.data.dataclasses.PlaceType
//import com.example.inzproject.WeatherForecast.domain.location.LocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
//import io.ktor.client.*
//import io.ktor.client.features.*
//import io.ktor.client.features.json.*
//import io.ktor.util.pipeline.StackWalkingFailedFrame.context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//import kotlinx.serialization.Serializable
import retrofit2.Response
import javax.inject.Inject
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
import com.example.inzproject.domain.location.LocationTracker

@HiltViewModel
class PlacesViewModel @Inject constructor(private val placeDao: PlaceDao, private val googlePlacesApi: CordinatesApi, private var locationTracker: LocationTracker,) : ViewModel() {
    private val _placesLiveData = MutableLiveData<List<PlaceClass>>()
    val placesLiveData: LiveData<List<PlaceClass>> get() = _placesLiveData

       //private var allplaces:Deffered<LiveData<List<PlaceClass>>> = placeDao.getAllPlaces()




    var filterKeyword by mutableStateOf("")
        private set
    var filterRadius by mutableStateOf(100)
        private set
    var filterMinRating by mutableStateOf(0.0)
        private set
    var filterMaxRating by mutableStateOf(5.0)
        private set
    var filterPlaceType by mutableStateOf(PlaceType(Icons.Default.ZoomOutMap, "Wszystko", ""))
        private set

    var filterLocalization by mutableStateOf("")
        private set


    val localizationCoordinates = MutableLiveData<String>("")



    var LocalizationCoordinates by mutableStateOf("")
        private set



 fun setLocalization(Localization:String){
     filterLocalization = Localization

 }

    private val _currentLocation = MutableLiveData<Pair<Double, Double>>()
    val currentLocation: LiveData<Pair<Double, Double>> get() = _currentLocation

    fun getCurrentLocation(context:Context) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Sprawdź uprawnienia do lokalizacji
        if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            // Uzyskaj ostatnią znaną lokalizację
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            // Jeśli jest dostępna, zaktualizuj LiveData
            lastKnownLocation?.let {
                _currentLocation.value = Pair(it.latitude, it.longitude)
            }

            // Ustaw LocationListener dla uzyskania aktualizacji lokalizacji
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000, // Czas w milisekundach między aktualizacjami
                1f,    // Minimalna zmiana odległości w metrach, aby uzyskać aktualizację
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        _currentLocation.value = Pair(location.latitude, location.longitude)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        // Nie używamy tego w tym przypadku
                    }

                    override fun onProviderEnabled(provider: String) {
                        // Nie używamy tego w tym przypadku
                    }

                    override fun onProviderDisabled(provider: String) {
                        // Nie używamy tego w tym przypadku
                    }
                }
            )
        } else {
            // Poproś użytkownika o nadanie uprawnień lokalizacyjnych
            // Tutaj możesz użyć odpowiedniej logiki do wywołania Intentu itp.
        }
    }


    fun setFilters(
        keyword: TextFieldValue,
        minRating: Double,
        maxRating: Double,
        radius: Int,
        placeType: PlaceType
    ) {
        filterKeyword = keyword.text
        filterMinRating = minRating
        filterMaxRating = maxRating
        filterRadius = radius
        filterPlaceType = placeType
    }

    var state by mutableStateOf(PlaceState())
        private set

    fun loadPlaces() {
        viewModelScope.launch {
            val places = placeDao.getAllPlaces()
           // _placesLiveData.postValue(places)

        }

    }

    fun savePlaces(places: List<PlaceClass>) {
        viewModelScope.launch {
            placeDao.insertPlaces(places)
        }
    }

    fun savePlace(place: PlaceClass) {
        viewModelScope.launch {
            placeDao.insertPlace(place)
        }
    }
fun setstate(placesResponse: PlacesResponse)
{
    state = state.copy(
                             PlaceInfo = placesResponse as PlacesResponse?,
                             isLoading = false,
                             error = null
                         )

}



    fun getlocationfromname(locationname:String) {
        viewModelScope.launch {
            val key = "AIzaSyCXMGGlLd0k2DNkBhC0tbTPr3tj4HutEJI"
//            val coordinates = GooglePlacesRepository(googlePlacesApi)
//                .getCoordinatesForPlace(filterLocalization, key)
//
//            val formattedCoordinates = "${coordinates?.latitude},${coordinates?.longitude}"
//            localizationCoordinates.postValue(formattedCoordinates)
//            println("moje koordynaty")
//            println(coordinates)
//        }


//            val result = withContext(Dispatchers.IO) {
//                RetrofitClient2.coordinatesapi.getPlaceCoordinates(
//                    address = "London",
//                    key = key
//                )
//
//            }
//
//
//
//            if (result.isSuccessful) {
//                val formattedCoordinates = "${result.latitude},${result?.longitude}"
//               println("to odpowiedz ")
//                println(result)
//
//            }
        }
    }

    fun getPlacesAsync(context: Context){
        // Tutaj korutyna dla funkcji getPlaces
     viewModelScope.launch {
         state = state.copy(
             isLoading = true,
             error = null
         )
         var canfindthelocation = true
         val key = "AIzaSyCXMGGlLd0k2DNkBhC0tbTPr3tj4HutEJI"

         if (filterLocalization == "") {

             println("puste")
             try {
                 locationTracker.getCurrentLocation()!!.let { location ->
//              var cordinates = getCoordinates(context)
                     if (location != null) {
                         LocalizationCoordinates = "${location?.latitude},${location?.longitude}"


                     } else {
                         canfindthelocation = false
                         kotlin.run {
                             state = state.copy(
                                 PlaceInfo = null,
                                 isLoading = false,
                                 error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                             )
                         }
                     }
                 }

             } catch (e: Exception) {
                 canfindthelocation = false
                 e.printStackTrace()
                 state = state.copy(
                     PlaceInfo = null,
                     isLoading = false,
                     error = "Couldn't retrieve location. Make sure to grant permission and enable GPS"
                 )
             }


         } else {


             //    val location = getLocationCoordinates(filterLocalization)


             try {
                 val coordinates = GooglePlacesRepository(googlePlacesApi)
                     .getCoordinatesForPlace(filterLocalization, key)
                 LocalizationCoordinates = "${coordinates?.latitude},${coordinates?.longitude}"
             } catch (e: Exception) {
                 canfindthelocation = false
                 e.printStackTrace()
                 state = state.copy(
                     PlaceInfo = null,
                     isLoading = false,
                     error = "can not find the specified location"
                 )
             }


         }
if(canfindthelocation){
         var isWaiting = true
         val loc = LocalizationCoordinates
         val radius = filterRadius.toString()
         val type = filterPlaceType
         val keyword = filterKeyword

         if (loc != "") {
             val result = withContext(Dispatchers.IO) {
                 RetrofitClient.placeApi.getPlaces(
                     loc = loc,
                     rad = radius,
                     type = type.id,
                     keyword = keyword,
                     key = key
                 )

             }



             if (result.isSuccessful) {

                 if (result.body()?.results?.isEmpty() == true) {

                     state = state.copy(
                         PlaceInfo = null,
                         isLoading = false,
                         error = "No places with the specified parameters were found"
                     )
                 } else {

                     state = state.copy(
                         PlaceInfo = result.body(),
                         isLoading = false,
                         error = null
                     )


                 }
             } else {

                 state = state.copy(
                     PlaceInfo = null,
                     isLoading = false,
                     error = "result.message"
                 )


//                 }
//             } catch (e: Exception) {
//                 e.printStackTrace()
//                 state = state.copy(
//                     isLoading = false,
//                     error = "An unknown error occurred."
//                 )
//
//             }
             }
         } else {
             state = state.copy(
                 PlaceInfo = null,
                 isLoading = false,
                 error = "connot find the specified location"
             )

         }
    isWaiting = false
     }


     }
         }
}







//private suspend fun getLocationCoordinates(locationName: String): Coordinates {
//    val geocodingResult = withContext(Dispatchers.IO) {
//        RetrofitClient.placeApi.geocodeLocation(
//            location = locationName,
//            key = "YOUR_API_KEY" // Zastąp tym swoim kluczem API
//        )
//    }
//
//    return if (geocodingResult.isSuccessful) {
//        val firstResult = geocodingResult.body()?.results?.firstOrNull()
//        firstResult?.geometry?.location?.let {
//            Coordinates(it.lat, it.lng)
//        } ?: Coordinates(0.0, 0.0) // Domyślne wartości w przypadku braku danych
//    } else {
//        Coordinates(0.0, 0.0) // Domyślne wartości w przypadku błędu
//    }
//}
//
//data class Coordinates(val latitude: Double, val longitude: Double)

//}
