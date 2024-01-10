package com.example.inzproject.helpfunctions

import com.google.android.gms.maps.model.LatLngBounds
import kotlin.random.Random

fun randLat(it: LatLngBounds) : Double = Random.nextDouble(
    it.southwest.latitude + (it.northeast.latitude - it.southwest.latitude) / 5,
    it.northeast.latitude - (it.northeast.latitude - it.southwest.latitude) / 5
)

fun randLng(it: LatLngBounds) : Double = Random.nextDouble(
    it.southwest.longitude + (it.northeast.longitude - it.southwest.longitude) / 5,
    it.northeast.longitude - (it.northeast.longitude - it.southwest.longitude) / 5
)