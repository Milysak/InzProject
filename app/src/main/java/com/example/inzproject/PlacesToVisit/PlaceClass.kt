package com.example.inzproject.PlacesToVisit

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.inzproject.PlacesToVisit.ROOM.Converters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
data class PlacesResponse(
    val results: List<PlaceClass>,
    val status: String
)

@Entity(tableName = "favouriteplaces")
data class PlaceClass(

    @PrimaryKey
    val place_id: String,
    val name: String,
    val rating: Double?,

 //  val opening_hours: OpeningHours?,


   //   val types: List<String>,

    val vicinity: String,
    val icon: String,

)

data class OpeningHours(
    val open_now: Boolean
){
    override fun toString(): String {
        return if (open_now) "Open Now" else "Closed"
    }

}