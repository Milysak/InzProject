package com.example.inzproject.PlacesToVisit

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
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

@Entity(tableName = "love_places")
data class PlaceClass(

    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val place_id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "rating")
    val rating: Double?,

 //  val opening_hours: OpeningHours?,


   //   val types: List<String>,

    @ColumnInfo(name = "vicinity")
    val vicinity: String,

    @ColumnInfo(name = "icon")
    val icon: String,

)

data class OpeningHours(
    val open_now: Boolean
){
    override fun toString(): String {
        return if (open_now) "Open Now" else "Closed"
    }

}