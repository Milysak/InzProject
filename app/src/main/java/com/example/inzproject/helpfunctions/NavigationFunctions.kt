package com.example.inzproject.helpfunctions

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import com.example.inzproject.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

fun navigateToByName(
    context: Context,
    viewModel: MapViewModel,
    map: GoogleMap
) {
    val geocoder = Geocoder(context)
    val addressList: List<Address>? = geocoder
        .getFromLocationName(
            viewModel.location,
            1
        )

    if (addressList?.isNotEmpty() == true) {
        val address: Address = addressList!![0]
        val latLng = LatLng(
            address.latitude,
            address.longitude
        )

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                12f
            ),
            400,
            null
        )
    } else {
        Toast.makeText(
            context,
            "Nie znaleziono takiego miejsca!",
            Toast.LENGTH_SHORT
        ).show()
    }

    viewModel.location = ""
}

fun navigateToByLatLng(
    viewModel: MapViewModel,
    map: GoogleMap
) {
    map.animateCamera(
        CameraUpdateFactory.newLatLngZoom(
            viewModel.coords!!, 12f
        ),
        400,
        null
    )

    viewModel.coords = null
}