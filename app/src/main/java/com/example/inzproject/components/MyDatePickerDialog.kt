package com.example.inzproject.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inzproject.viewmodels.WeatherViewModel
import io.ktor.util.*
import io.ktor.util.date.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, InternalAPI::class)
@Composable
fun MyDatePickerDialog(
    weatherViewModel: WeatherViewModel,
    selectedDay: Int,
    onSelectedDayChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val minDate: Calendar = Calendar.getInstance()
    val maxDate: Calendar = Calendar.getInstance()
    maxDate.add(Calendar.DAY_OF_MONTH, 6)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = minDate.timeInMillis,
        selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return (
                    utcTimeMillis > minDate.timeInMillis - (24 * 60 * 60 * 1000)
                    ) && (
                    utcTimeMillis < maxDate.timeInMillis
                    )
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
                           },
        confirmButton = {
            Button(onClick = {
                 val difference = (
                         datePickerState.selectedDateMillis?.plus(24 * 60 * 60 * 1000)?.minus(
                             minDate.timeInMillis)
                         )?.div(
                     (1000 * 60 * 60 * 24)
                 )

                if (difference != null) {
                    onSelectedDayChange(difference.toInt())
                }

                 onDismiss()
                }
            ) {
                Text(text = "Ustaw")
            }
        },
        dismissButton = {
            Button(onClick = {
                 onDismiss()
            }) {
                Text(text = "Anuluj")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }
}