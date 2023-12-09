package com.example.inzproject.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inzproject.R

@Composable
fun WeatherFrontCard() {
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp),
                text = "18" + "\u2103",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                modifier = Modifier
                    .size(85.dp)
                    .padding(top = 20.dp, end = 20.dp),
                painter = painterResource(id = R.mipmap.ic_logo_foreground), contentDescription = "Logo"
            )

        }

        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = "Katowice",
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun WeatherFrontCardPreview() {
    WeatherFrontCard()
}