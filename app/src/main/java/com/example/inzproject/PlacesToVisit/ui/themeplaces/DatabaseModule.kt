package com.example.inzproject.PlacesToVisit.ui.themeplaces

import android.content.Context
import androidx.room.Room
import com.example.inzproject.PlacesToVisit.ROOM.FavouritePlacesDatabase
import com.example.inzproject.PlacesToVisit.ROOM.PlaceDao
//import com.example.inzproject.PlacesToVisit.ROOM.PlaceDatabase
//import com.example.inzproject.PlacesToVisit.ROOM.PlaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

//
//    @Singleton
//    @Provides
//    fun provideDatabase(
//
//        @ApplicationContext context: Context
//    )= Room.databaseBuilder(context,FavouritePlacesDatabase::class.java,"favouriteplacesdatabase").createFromAsset("database/Favourites.db").build()
//
//    @Singleton
//    @Provides
//    fun provideDao(database: FavouritePlacesDatabase) = database.placeDao()




    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavouritePlacesDatabase {
        return Room.databaseBuilder(context, FavouritePlacesDatabase::class.java, FavouritePlacesDatabase.DB_NAME)
            //.createFromAsset("database/Favourites.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: FavouritePlacesDatabase): PlaceDao {
        return database.placeDao()
    }
}