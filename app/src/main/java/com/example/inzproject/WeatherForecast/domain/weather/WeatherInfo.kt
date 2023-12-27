package com.example.inzproject.WeatherForecast.domain.weather
//ta klasa przechowuje dane podzielone posiada liste z danymi na kazdy dzien i obecna pogode
data class WeatherInfo(

    val weatherDataPerDay: Map<Int, List<WeatherData>>,

    val currentWeatherData: WeatherData?
)
