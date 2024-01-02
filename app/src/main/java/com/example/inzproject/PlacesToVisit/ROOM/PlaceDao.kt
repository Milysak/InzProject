package com.example.inzproject.PlacesToVisit.ROOM

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
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



    @Delete
    suspend fun deletePlace(placeclass: PlaceClass)

    @Query("SELECT * FROM love_places")
    suspend fun getAllPlaces(): List<PlaceClass>

    @Query("DELETE FROM love_places WHERE place_id = :placeId")
    suspend fun deletePlaceById(placeId: String)

    @Query("DELETE FROM love_places")
    suspend fun clearAllPlaces()

    @Query("SELECT EXISTS (SELECT 1 FROM love_places WHERE place_id = :placeId)")
    suspend fun checkIfPlaceExists(placeId: String): Boolean
}