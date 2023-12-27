package com.example.inzproject.PlacesToVisit.ROOM

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy {
        Room.databaseBuilder(this, FavouritePlacesDatabase::class.java, "favouriteplaces_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}