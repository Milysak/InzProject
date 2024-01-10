package com.example.inzproject.helpfunctions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.inzproject.R
import com.example.inzproject.data.MarkerType
import com.example.inzproject.viewmodels.MapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.CameraPositionState

@RequiresApi(Build.VERSION_CODES.R)
fun reorganizeWeatherMarkers(
    context: Context,
    viewModel: MapViewModel,
    map: GoogleMap,
    currentScreen: LatLngBounds?,
    cameraPositionState: CameraPositionState
) {
    fun createMarker() {
        currentScreen?.let {
            if (viewModel.markers.size < 3) {
                val lat = randLat(it)
                val lng = randLng(it)

                viewModel.getWeather(
                    latitude = lat,
                    longitude = lng,
                    onError = { weather ->
                        viewModel.markers.add(
                            map.addMarker(
                                MarkerOptions()
                                    .position(
                                        LatLng(
                                            lat,
                                            lng
                                        )
                                    )
                                    .icon(
                                        bitmapDescriptorFromVector(
                                            context,
                                            R.drawable.notloadedicon_foreground,
                                            MarkerType.Weather.toString()
                                        )
                                    )
                                    .title(
                                        MarkerType.Weather.toString()
                                    )
                                    .snippet(
                                        "Błąd "
                                    )
                            )
                        )
                    }
                ) { weather ->
                    val temp: Double? = weather?.temperatureCelsius

                    viewModel.markers.add(
                        map.addMarker(
                            MarkerOptions()
                                .position(
                                    LatLng(
                                        lat,
                                        lng
                                    )
                                )
                                .icon(
                                    bitmapDescriptorFromVector(
                                        context,
                                        weather?.weatherType?.iconRes
                                            ?: R.drawable.notloadedicon_foreground,
                                        MarkerType.Weather.toString()
                                    )
                                )
                                .title(
                                    MarkerType.Weather.toString()
                                )
                                .snippet(
                                    temp?.toString()
                                )
                        )
                    )
                }
            }
        }
    }

    currentScreen?.let {
        if (viewModel.markers.isEmpty()) {
            for (i in 0..2) {
                createMarker()
            }
        } else {
            viewModel.markers.forEach { marker ->
                if (
                    marker?.position?.latitude!! in it.southwest.latitude..it.northeast.latitude
                    && marker?.position?.longitude!! in it.southwest.longitude..it.northeast.longitude
                ) {
                    if (Math.abs(viewModel.cameraZoom - cameraPositionState.position.zoom) > 0.5f) {
                        marker.remove()

                        viewModel.markers =
                            viewModel.markers.filter { it != marker } as MutableList<Marker?>

                        createMarker()
                    } else {
                        // Do nothing
                    }
                } else {
                    marker.remove()

                    viewModel.markers =
                        viewModel.markers.filter { it != marker } as MutableList<Marker?>

                    createMarker()
                }
            }
        }
    }

    viewModel.cameraZoom = cameraPositionState.position.zoom
}