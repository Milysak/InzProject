package com.example.inzproject.components

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inzproject.R

@Composable
fun WeatherBackCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.35f)
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.125f),
            MaterialTheme.colorScheme.onBackground,
            Color.Transparent,
            Color.Transparent
        ),
    ) {
        val data = listOf("Wind Speed", "Pressure", "Air Humidity", "Visibility")

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            items(data) { item ->
                Box(
                    modifier = Modifier
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(55.dp),
                            painter = painterResource(id = R.mipmap.ic_logo_foreground), contentDescription = "Logo"
                        )
                        Text(
                            text = item,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherBackCardPreview() {
    WeatherBackCard()
}