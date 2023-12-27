package com.example.inzproject.di

import com.example.inzproject.WeatherForecast.domain.repository.WeatherRepository2
import com.example.inzproject.data.repository.WeatherRepositoryImpl
import com.example.inzproject.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository2(
        weatherRepositoryImpl: com.example.inzproject.WeatherForecast.data.repository.WeatherRepositoryImpl
    ): WeatherRepository2




}