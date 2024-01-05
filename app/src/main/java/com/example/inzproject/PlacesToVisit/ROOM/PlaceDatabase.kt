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

        const val DB_NAME = "love_places"

        @Volatile
        private var INSTANCE: FavouritePlacesDatabase? = null

        fun getInstance(context: Context): FavouritePlacesDatabase {
            // only one thread of execution at a time can enter this block of code
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavouritePlacesDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
