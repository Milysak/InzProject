package com.example.inzproject.PlacesToVisit.ROOM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.inzproject.PlacesToVisit.Place
import com.example.inzproject.PlacesToVisit.PlaceClass

@Database(entities = [PlaceClass::class], version = 3, exportSchema = false)
abstract class FavouritePlacesDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var INSTANCE: FavouritePlacesDatabase? = null


//        fun getInstance(context: Context): FavouritePlacesDatabase? {
//
//            if(INSTANCE==null) {
//
//             INSTANCE = Room.databaseBuilder(
//                context,
//                FavouritePlacesDatabase::class.java,
//                "favouriteplaces")
//                 .fallbackToDestructiveMigration()
//                 .build()
//
//            }
//return INSTANCE
//        }

            fun getDatabase(context: Context): FavouritePlacesDatabase? {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavouritePlacesDatabase::class.java,
                        "favouriteplaces"
                    ).build()
                    INSTANCE = instance
                instance
            }
        }
    }
}
