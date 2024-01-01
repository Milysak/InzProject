package com.example.inzproject.PlacesToVisit
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
@Entity(tableName = "Places"
//    , indices = [Index(value = ["placeName", "cityName"], unique = true)]
)
data class Place(

    @PrimaryKey(autoGenerate = true)
    val id: Int=0,

    val type: String,

    val image: Int = 0,
    val placeName: String,
    val cityName: String,
    val availabilityHours: String,
    val googleNote: Double,

)
