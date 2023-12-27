package com.example.inzproject.WeatherForecast.domain.weather

import java.time.LocalDateTime
//tu pokazane są dane pogodowe dotyczące każdej godziny
data class WeatherData(

    val time: LocalDateTime,
    val weatherType: WeatherType,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,

)
