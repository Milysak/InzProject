
package com.example.inzproject.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inzproject.R
import com.example.inzproject.WeatherForecast.domain.location.LocationTracker
import com.example.inzproject.WeatherForecast.presentation.WeatherCard
import com.example.inzproject.WeatherForecast.presentation.WeatherForecast
import com.example.inzproject.WeatherForecast.presentation.ui.theme.DarkBlue
import com.example.inzproject.WeatherForecast.presentation.ui.theme.DeepBlue
import com.example.inzproject.viewmodels.MainViewModel
import com.example.inzproject.viewmodels.WeatherViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.*
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
) {


    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    var selectedDay by remember { mutableStateOf(0) }

    val context = LocalContext.current
    // val newLocationTracker = LocationTracker()
    //viewModel.updateParams(newRepository, newLocationTracker, newCityName)
    if(viewModel.state.weatherInfo==null && viewModel.state.error==null && viewModel.textState =="") {
        LaunchedEffect(key1 = Unit) {
            var res = viewModel.loadWeatherInfo(context = context, "mylocation", selectedDay)


        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

//viewModel.fetchDataFromApi(10.1,20.4)


    }


    val minDate = Calendar.getInstance()
    val maxDate = Calendar.getInstance()
    maxDate.add(Calendar.DAY_OF_MONTH, 7)


    Box(


    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            showDatePicker(context, minDate, maxDate,viewModel, selectedDay){ newSelectedDay ->
                selectedDay = newSelectedDay // Update the selectedDay state
            }

            Spacer(modifier = Modifier.size(10.dp))

            WeatherCard(
                state = viewModel.state,
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.125f)
            )

            Spacer(modifier = Modifier.height(5.dp))

            WeatherForecast(state = viewModel.state)
        }
        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        viewModel.state.error?.let { error ->
            androidx.compose.material.Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }


    }
}

@Composable
fun showDatePicker(context: Context, minDate: Calendar, maxDate: Calendar,weatherViewModel: WeatherViewModel,selectedDay:Int,  onSelectedDayChange: (Int) -> Unit) {
    val currentDate = remember { Calendar.getInstance() }
    val selectedDate = remember { mutableStateOf(currentDate) }
    var cityName by remember { mutableStateOf("") }
    var textState by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var city:String=""
    var citycoordinates : String = ""
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selected = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                if (selected >= minDate && selected <= maxDate) {
                    selectedDate.value = selected
                    val difference = (selected.timeInMillis - currentDate.timeInMillis) / (1000 * 60 * 60 * 24)
                    onSelectedDayChange(difference.toInt())
                }
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
    }

    datePickerDialog.datePicker.minDate = minDate.timeInMillis
    datePickerDialog.datePicker.maxDate = maxDate.timeInMillis

    Column(
        modifier = Modifier.height(90.dp).padding(horizontal = 10.dp),
    ) {
        androidx.compose.material.Text(
            text = "Wybrana Data: ${selectedDate.value.get(Calendar.DAY_OF_MONTH)}/${
                selectedDate.value.get(
                    Calendar.MONTH
                ) + 1
            }/${selectedDate.value.get(Calendar.YEAR)}",
            color = Color.White
        )
        Spacer(modifier = Modifier.size(5.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()

        ) {

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(DeepBlue, shape = CircleShape),

                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        datePickerDialog.show()

                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_calendar_month_24),
                        contentDescription = "Przycisk Kalendarz",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.8f))

            TextInputDemo(textState) { newTextInput ->
                textState = newTextInput
            }
            Spacer(modifier = Modifier.weight(0.8f))
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(DeepBlue, shape = CircleShape),

                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        city = textState
                        if(city=="" || city == null){
                            city="mylocation"
                        }
                        println(textState)
                        weatherViewModel.settext(city)
                        //   weatherViewModel.fetchDataFromApi(10.1,19.2)
                        weatherViewModel.loadWeatherInfo(context = context, city, selectedDay)
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Wyszukaj",
                        tint = Color.White
                    )
                }
            }

            androidx.compose.material.Text(
                text = citycoordinates,

                color = Color.White
            )


        }
    }
}
@Composable
fun TextInputDemo(text: String, onTextChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .width(200.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        var currentText by remember { mutableStateOf(text) } // Dodaj zmienną stanu

        BasicTextField(
            value = currentText,
            onValueChange = {
                currentText = it // Aktualizuj wartość zmiennej stanu
                onTextChange(it) // Aktualizuj wartość tekstu za pomocą funkcji przekazanej jako parametr
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color.White,
                )
        )
    }
}


//@Composable
//fun showDatePicker(context: Context, minDate: Calendar, maxDate: Calendar, selectedDay: Int) {
//    val currentDate = remember { Calendar.getInstance() }
//    val selectedDate = remember { mutableStateOf(currentDate) }
//    var cityName by remember { mutableStateOf("") }
//    var textState by remember { mutableStateOf("") }
//    var isSearching by remember { mutableStateOf(false) }
//    var city:String=""
//    var citycoordinates : String = ""
//    val datePickerDialog = remember {
//        DatePickerDialog(
//            context,
//            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
//                val selected = Calendar.getInstance().apply {
//                    set(year, month, dayOfMonth)
//                }
//                if (selected >= minDate && selected <= maxDate) {
//                    selectedDate.value = selected
//                    val difference = (selected.timeInMillis - currentDate.timeInMillis) / (1000 * 60 * 60 * 24)
//                    onSelectedDayChange(difference.toInt())
//                }
//            },
//            currentDate.get(Calendar.YEAR),
//            currentDate.get(Calendar.MONTH),
//            currentDate.get(Calendar.DAY_OF_MONTH)
//        )
//    }
//
//    datePickerDialog.datePicker.minDate = minDate.timeInMillis
//    datePickerDialog.datePicker.maxDate = maxDate.timeInMillis
//
//    Column(
//        modifier = Modifier.height(90.dp),
//    ) {
//        androidx.compose.material.Text(
//            text = "Wybrana Data: ${selectedDate.value.get(Calendar.DAY_OF_MONTH)}/${
//                selectedDate.value.get(
//                    Calendar.MONTH
//                ) + 1
//            }/${selectedDate.value.get(Calendar.YEAR)}",
//            color = Color.White
//        )
//        Spacer(modifier = Modifier.size(5.dp))
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxSize()
//
//        ) {
//
//            Box(
//                modifier = Modifier
//                    .size(56.dp)
//                    .background(DeepBlue, shape = CircleShape),
//
//                contentAlignment = Alignment.Center
//            ) {
//                IconButton(
//                    onClick = {
//                        datePickerDialog.show()
//
//                    },
//                    modifier = Modifier.size(48.dp)
//                ) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(R.drawable.baseline_calendar_month_24),
//                        contentDescription = "Przycisk Kalendarz",
//                        tint = Color.White
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.weight(0.8f))
//
//            TextInputDemo(textState) { newTextInput ->
//                textState = newTextInput
//            }
//            Spacer(modifier = Modifier.weight(0.8f))
//            Box(
//                modifier = Modifier
//                    .size(56.dp)
//                    .background(DeepBlue, shape = CircleShape),
//
//                contentAlignment = Alignment.Center
//            ) {
//                IconButton(
//                    onClick = {
//                        city = textState
//                        if(city=="" || city == null){
//                            city="mylocation"
//                        }
//                        println(textState)
//                        viewModel.loadWeatherInfo(context = context, city, selectedDay)
//                    },
//                    modifier = Modifier.size(48.dp)
//                ) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
//                        contentDescription = "Wyszukaj",
//                        tint = Color.White
//                    )
//                }
//            }
//
//            androidx.compose.material.Text(
//                text = citycoordinates,
//
//                color = Color.White
//            )
//
//
//        }
//    }
//}
//@Composable
//fun TextInputDemo(text: String, onTextChange: (String) -> Unit) {
//    Box(
//        modifier = Modifier
//            .padding(5.dp)
//            .width(200.dp)
//            .background(
//                color = Color.White,
//                shape = RoundedCornerShape(30.dp)
//            )
//    ) {
//        var currentText by remember { mutableStateOf(text) } // Dodaj zmienną stanu
//
//        BasicTextField(
//            value = currentText,
//            onValueChange = {
//                currentText = it // Aktualizuj wartość zmiennej stanu
//                onTextChange(it) // Aktualizuj wartość tekstu za pomocą funkcji przekazanej jako parametr
//            },
//            textStyle = TextStyle(
//                fontSize = 16.sp,
//                color = Color.Black
//            ),
//            modifier = Modifier
//                .padding(16.dp)
//                .background(
//                    color = Color.White,
//                )
//        )
//    }

