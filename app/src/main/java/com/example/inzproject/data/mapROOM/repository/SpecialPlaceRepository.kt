package com.example.inzproject.data.mapROOM.repository

import com.example.inzproject.data.mapROOM.database.SpecialPlace
import com.example.inzproject.data.mapROOM.database.SpecialPlaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SpecialPlaceRepository @Inject constructor(
    private val specialPlaceDao: SpecialPlaceDao
    ) {

    val allSpecialPlaces: Flow<List<SpecialPlace>> = specialPlaceDao.getAllPlaces()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addSpecialPlace(newSpecialPlace: SpecialPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            specialPlaceDao.addPlace(newSpecialPlace)
        }
    }

    fun deleteSpecialPlace(specialPlace: SpecialPlace) {
        coroutineScope.launch(Dispatchers.IO) {
            specialPlaceDao.deletePlace(
                specialPlace.itemTitle,
                specialPlace.latitute,
                specialPlace.longitute
            )
        }
    }

}