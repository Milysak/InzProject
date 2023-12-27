package com.example.inzproject.PlacesToVisit.ROOM

import androidx.room.TypeConverter
import com.example.inzproject.PlacesToVisit.OpeningHours
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.let {
            // Konwersja ciągu JSON na listę stringów
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(value, listType)
        }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.let {
            // Konwersja listy stringów na ciąg JSON
            Gson().toJson(it)
        }
    }

}