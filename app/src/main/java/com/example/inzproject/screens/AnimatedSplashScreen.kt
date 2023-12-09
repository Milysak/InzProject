package com.example.inzproject.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_CAR
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inzproject.helpfunctions.createGradientBrush
import com.example.inzproject.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1250
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        navController.popBackStack()
        navController.navigate(Routes.Main.route)
    }

    SplashScreen(alpha = alphaAnimation.value)
}

@Composable
fun SplashScreen(alpha: Float) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            MaterialTheme.colorScheme.background
            /*createGradientBrush(
                listOf(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                )
            )*/
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .alpha(alpha = alpha),
            painter = painterResource(id = com.example.inzproject.R.mipmap.ic_logo_foreground), contentDescription = "Logo")
        Text(
            modifier = Modifier
                .alpha(alpha = alpha),
            fontSize = 20.sp,
            text = "Travel App",
            color = MaterialTheme.colorScheme.onBackground,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
    }

    Box() {
        
    }
}

@Composable
@Preview
fun SplashPreview() {
    SplashScreen(alpha = 1f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashPreviewDarkMode() {
    SplashScreen(alpha = 1f)
}