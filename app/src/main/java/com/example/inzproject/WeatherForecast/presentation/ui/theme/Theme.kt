package com.example.inzproject.WeatherForecast.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Typography

import androidx.compose.runtime.Composable

@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}