package com.example.inzproject.screens

import android.graphics.drawable.shapes.OvalShape
import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inzproject.R
import com.example.inzproject.components.FlipCard
import com.example.inzproject.components.WeatherFrontCard
import com.example.inzproject.helpfunctions.createGradientBrush

@Composable
fun HomeScreen() {
    Column(modifier = Modifier
        .fillMaxSize(),
    ) {
        Box(modifier = Modifier
            .padding(top = 20.dp)
        ) {
            FlipCard()
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}