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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.CameraPositionState

@RequiresApi(Build.VERSION_CODES.R)
fun reorganizeSpecialPlacesMarkers(
    context: Context,
    viewModel: MapViewModel,
    map: GoogleMap
) {
    viewModel.specialMarkers.forEach {
        it?.remove()
    }

    viewModel.specialPlacesList.value?.forEach {
        viewModel.specialMarkers.add(
            map.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(it.latitute, it.longitute),
                    )
                    .icon(
                        bitmapDescriptorFromVectorForSpecialPlaces(
                            context,
                            R.drawable.marker_foreground,
                        ),
                    )
                    .title(
                        MarkerType.SpecialPlace.toString(),
                    )
                    .snippet(
                        it.itemTitle,
                    ),
            ),
        )
    }
}