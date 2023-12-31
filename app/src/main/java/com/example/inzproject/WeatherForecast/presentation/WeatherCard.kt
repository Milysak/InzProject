package com.example.inzproject.WeatherForecast.presentation
import com.example.inzproject.WeatherForecast.domain.weather.WeatherData
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inzproject.R
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.math.roundToInt
import com.example.inzproject.WeatherForecast.presentation.ui.theme.DarkBlue
import androidx.compose.material.Card as Card1
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.toUpperCase
import com.example.inzproject.components.FlipCard

//tu znajduję się duża główna karta z pogodą z obecnej chwili
@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {

//var data : WeatherData
    val context = LocalContext.current
    var cityOfLocalization = state.city
    cityOfLocalization = cityOfLocalization[0].uppercase() + cityOfLocalization.drop(1)

    state.weatherInfo?.currentWeatherData?.let { data ->

        FlipCard(
            frontCard = {
                FrontCard(
                    modifier = modifier,
                    data = data,
                    cityOfLocalization = cityOfLocalization,
                    backgroundColor = backgroundColor
                )
            },
            backCard = {
                BackCard(
                    modifier = modifier,
                    data = data,
                    backgroundColor = backgroundColor
                )
            }
        )
    }
}

@Composable
fun FrontCard(
    modifier: Modifier,
    data: WeatherData,
    cityOfLocalization: String,
    backgroundColor: Color
) {
    Card1(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = backgroundColor,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Dzisiaj ${
                    data.time.format(
                        DateTimeFormatter.ofPattern("HH:mm")
                    )
                }",
                modifier = Modifier.align(Alignment.End),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = cityOfLocalization,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                style = TextStyle(fontSize = 30.sp)

            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = data.weatherType.iconRes),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = "${data.temperatureCelsius}°C",
                fontSize = 40.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun BackCard(
    modifier: Modifier,
    data: WeatherData,
    backgroundColor: Color
) {
    val scale = 1.5f

    Card1(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = backgroundColor,
        modifier = modifier.padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                WeatherDataDisplay(
                    value = data.windSpeed.roundToInt(),
                    unit = " km/h",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White),
                    scale = scale
                )


                WeatherDataDisplay(
                    value = data.pressure.roundToInt(),
                    unit = " hPa",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White),
                    scale = scale
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                WeatherDataDisplay(
                    value = data.humidity.roundToInt(),
                    unit = " %",
                    icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                    iconTint = Color.White,
                    textStyle = TextStyle(color = Color.White),
                    scale = scale
                )
            }
        }
    }
}




