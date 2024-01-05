package com.example.inzproject.PlacesToVisit.ROOM

import androidx.room.*
import com.example.inzproject.PlacesToVisit.PlaceClass
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(placeclass: List<PlaceClass>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(placeclass: PlaceClass)

    @Transaction
    suspend fun toggleFavoritePlace(place: PlaceClass) {
        if (checkIfPlaceExists(placeId = place.place_id)) {
            deletePlace(place)
        } else {
            insertPlace(place)
        }
    }

    @Delete
    suspend fun deletePlace(placeclass: PlaceClass)

    @Query("SELECT * FROM love_places")
    suspend fun getAllPlaces(): List<PlaceClass>

    @Query("SELECT * FROM love_places")
    fun observeFavoritePlaces(): Flow<List<PlaceClass>>

    @Query("DELETE FROM love_places WHERE place_id = :placeId")
    suspend fun deletePlaceById(placeId: String)

    @Query("DELETE FROM love_places")
    suspend fun clearAllPlaces()

    @Query("SELECT EXISTS (SELECT 1 FROM love_places WHERE place_id = :placeId)")
    suspend fun checkIfPlaceExists(placeId: String): Boolean

    @Query("SELECT place_id from love_places")
    fun getIdList(): List<String>
}