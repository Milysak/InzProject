package com.example.inzproject.data.mapROOM.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(SpecialPlace::class)], version = 1, exportSchema = false)
abstract class SpecialPlaceRoomDatabase : RoomDatabase() {

    abstract fun specialPlaceDao(): SpecialPlaceDao

    companion object {

        @Volatile
        private var INSTANCE: SpecialPlaceRoomDatabase? = null

        fun getInstance(context: Context): SpecialPlaceRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SpecialPlaceRoomDatabase::class.java,
                        "special_places_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}