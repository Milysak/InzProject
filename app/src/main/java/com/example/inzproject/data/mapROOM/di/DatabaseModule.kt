package com.example.inzproject.data.mapROOM.di

import android.content.Context
import androidx.room.Room
import com.example.inzproject.data.mapROOM.database.SpecialPlaceDao
import com.example.inzproject.data.mapROOM.database.SpecialPlaceRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEmployeeDao(appDatabase: SpecialPlaceRoomDatabase): SpecialPlaceDao {
        return appDatabase.specialPlaceDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): SpecialPlaceRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SpecialPlaceRoomDatabase::class.java,
            "appDB"
        ).build()
    }

}