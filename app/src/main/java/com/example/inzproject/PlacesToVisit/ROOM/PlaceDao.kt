package com.example.inzproject.PlacesToVisit.ROOM

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inzproject.PlacesToVisit.PlaceClass

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(placeclass: List<PlaceClass>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(placeclass: PlaceClass)

    @Query("SELECT * FROM favouriteplaces")
    suspend fun getAllPlaces(): List<PlaceClass>

    @Query("DELETE FROM favouriteplaces WHERE place_id = :placeId")
    suspend fun deletePlaceById(placeId: String)

    @Query("DELETE FROM favouriteplaces")
    suspend fun clearAllPlaces()
}