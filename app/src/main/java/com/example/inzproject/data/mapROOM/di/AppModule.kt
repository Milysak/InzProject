package com.example.inzproject.data.mapROOM.di

import com.example.inzproject.data.mapROOM.database.SpecialPlaceDao
import com.example.inzproject.data.mapROOM.repository.SpecialPlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEmployeeRepository(specialPlaceDao: SpecialPlaceDao): SpecialPlaceRepository {
        return SpecialPlaceRepository(specialPlaceDao)
    }

}