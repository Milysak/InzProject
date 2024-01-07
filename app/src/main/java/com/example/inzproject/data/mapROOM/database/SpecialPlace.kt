package com.example.inzproject.data.mapROOM.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "special_places")
data class SpecialPlace(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "latitute")
    val latitute: Double,

    @ColumnInfo(name = "longitute")
    val longitute: Double,

    @ColumnInfo(name = "itemTitle")
    val itemTitle: String

    )