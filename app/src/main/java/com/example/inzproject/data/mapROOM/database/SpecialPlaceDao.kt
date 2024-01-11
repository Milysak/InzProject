package com.example.inzproject.data.mapROOM.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialPlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: SpecialPlace)

    @Query("DELETE FROM special_places WHERE itemTitle = :itemTitle AND latitute = :latitute AND longitute = :longitute")
    suspend fun deletePlace(itemTitle: String, latitute: Double, longitute: Double)

    @Query("SELECT * FROM special_places")
    fun getAllPlaces(): Flow<List<SpecialPlace>>

}