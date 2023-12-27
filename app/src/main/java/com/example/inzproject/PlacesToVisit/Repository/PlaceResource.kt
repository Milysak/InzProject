package com.example.inzproject.PlacesToVisit.Repository

sealed class PlaceResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): PlaceResource<T>(data)
    class Error<T>(message: String, data: T? = null): PlaceResource<T>(data, message)
}