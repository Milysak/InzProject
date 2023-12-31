package com.example.inzproject.WeatherForecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inzproject.WeatherForecast.domain.weather.WeatherData
import java.util.*
//tu znajduję się pasek z kartami na każdą godzinę
@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {

    var dayindex = state.day

    var currentDate = Calendar.getInstance()
    var calendardate = calculateDateFromDayDifference(currentDate, dayindex)

    val day = calendardate.get(Calendar.DAY_OF_MONTH)
    val month = calendardate.get(Calendar.MONTH) + 1 // Miesiące w Calendar są indeksowane od 0
    val year = calendardate.get(Calendar.YEAR)


    state.weatherInfo?.weatherDataPerDay?.get(dayindex)?.let { data->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {

            Text(
                text = "Prognoza na: ${day.toString().padStart(2, '0')}.${month.toString().padStart(2, '0')}.$year",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(5.dp))

            LazyRow(content = {
                items(data) { weatherData ->
                    //if (weatherData.time > state.weatherInfo.currentWeatherData?.time) {
                        HourlyWeatherDisplay(
                            weatherData = weatherData,
                            modifier = Modifier
                                .height(120.dp)
                                .width(100.dp)
                                .padding(end = 5.dp)
                        )
                    }
                //}
            }
            )
        }
    }
}

fun calculateDateFromDayDifference(today: Calendar, dayDifference: Int): Calendar {
    val selectedDate = today.clone() as Calendar
    selectedDate.add(Calendar.DAY_OF_MONTH, dayDifference)
    return selectedDate
}