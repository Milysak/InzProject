package com.example.inzproject.WeatherForecast.domain.weather

import androidx.annotation.DrawableRes
import com.example.inzproject.R

sealed class WeatherType(
    val weatherDescription: String,
    @DrawableRes val iconRes: Int
) {


    object ClearSky : WeatherType(
        weatherDescription = "Clear sky",
        iconRes = R.drawable.ic_sunny
    )

    object DenseDrizzle : WeatherType(
        weatherDescription = "Dense drizzle",
        iconRes = R.drawable.ic_rainshower
    )

    object DenseFreezingDrizzle : WeatherType(
        weatherDescription = "Dense freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy
    )

    object DepositingRimeFog : WeatherType(
        weatherDescription = "Depositing rime fog",
        iconRes = R.drawable.ic_very_cloudy
    )

    object Foggy : WeatherType(
        weatherDescription = "Foggy",
        iconRes = R.drawable.ic_very_cloudy
    )

    object HeavyFreezingRain : WeatherType(
        weatherDescription = "Heavy freezing rain",
        iconRes = R.drawable.ic_snowyrainy
    )

    object HeavyHailThunderstorm : WeatherType(
        weatherDescription = "Thunderstorm with heavy hail",
        iconRes = R.drawable.ic_rainythunder
    )

    object HeavyRain : WeatherType(
        weatherDescription = "Heavy rain",
        iconRes = R.drawable.ic_rainy
    )

    object HeavySnowFall : WeatherType(
        weatherDescription = "Heavy snow fall",
        iconRes = R.drawable.ic_heavysnow
    )

    object HeavySnowShowers : WeatherType(
        weatherDescription = "Heavy snow showers",
        iconRes = R.drawable.ic_snowy
    )

    object LightDrizzle : WeatherType(
        weatherDescription = "Light drizzle",
        iconRes = R.drawable.ic_rainshower
    )

    object LightFreezingDrizzle : WeatherType(
        weatherDescription = "Slight freezing drizzle",
        iconRes = R.drawable.ic_snowyrainy
    )

    object LightRainShowers : WeatherType(
        weatherDescription = "Slight rain showers",
        iconRes = R.drawable.ic_rainshower
    )

    object MainlyClear : WeatherType(
        weatherDescription = "Mainly clear",
        iconRes = R.drawable.ic_cloudy
    )

    object ModerateDrizzle : WeatherType(
        weatherDescription = "Moderate drizzle",
        iconRes = R.drawable.ic_rainshower
    )

    object ModerateHailThunderstorm : WeatherType(
        weatherDescription = "Thunderstorm with moderate hail",
        iconRes = R.drawable.ic_rainythunder
    )

    object ModerateRain : WeatherType(
        weatherDescription = "Rainy",
        iconRes = R.drawable.ic_rainy
    )

    object ModerateRainShowers : WeatherType(
        weatherDescription = "Moderate rain showers",
        iconRes = R.drawable.ic_rainshower
    )

    object ModerateSnowFall : WeatherType(
        weatherDescription = "Moderate snow fall",
        iconRes = R.drawable.ic_heavysnow
    )

    object ModerateSnowShowers : WeatherType(
        weatherDescription = "Moderate snow showers",
        iconRes = R.drawable.ic_snowy
    )

    object ModerateThunderstorm : WeatherType(
        weatherDescription = "Moderate thunderstorm",
        iconRes = R.drawable.ic_thunder
    )

    object Overcast : WeatherType(
        weatherDescription = "Overcast",
        iconRes = R.drawable.ic_cloudy
    )

    object PartlyCloudy : WeatherType(
        weatherDescription = "Partly cloudy",
        iconRes = R.drawable.ic_cloudy
    )

    object SlightHailThunderstorm : WeatherType(
        weatherDescription = "Thunderstorm with slight hail",
        iconRes = R.drawable.ic_rainythunder
    )

    object SlightRain : WeatherType(
        weatherDescription = "Slight rain",
        iconRes = R.drawable.ic_rainy
    )

    object SlightRainShowers : WeatherType(
        weatherDescription = "Slight rain showers",
        iconRes = R.drawable.ic_rainshower
    )

    object SlightSnowFall : WeatherType(
        weatherDescription = "Slight snow fall",
        iconRes = R.drawable.ic_snowy
    )

    object SlightSnowShowers : WeatherType(
        weatherDescription = "Light snow showers",
        iconRes = R.drawable.ic_snowy
    )

    object SnowGrains : WeatherType(
        weatherDescription = "Snow grains",
        iconRes = R.drawable.ic_heavysnow
    )

    object ViolentRainShowers : WeatherType(
        weatherDescription = "Violent rain showers",
        iconRes = R.drawable.ic_rainshower
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}