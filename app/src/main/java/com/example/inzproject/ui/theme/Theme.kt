package com.example.inzproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val CustomDarkColorScheme = darkColorScheme(
    primary = Color(0xFFCFDFFA),
    onPrimary = Color(0xFF032B55),
    background = Color(0xFF000D1B),
    secondary = Color(0xFF041429),
    onSecondary = Color(0xFFEFF5FD),
    tertiary = Pink80,
    onSurface = Color(0xFFEBF0FD),
    outline = Color(0xFFEBF0FD).copy(alpha = 0.75f),
    onSurfaceVariant = Color(0xFFEBF0FD).copy(alpha = 0.75f)
)

private val CustomLightColorScheme = lightColorScheme(
    primary = Color(0xFF012F5E),
    onPrimary = Color(0xFFEDF4FF),
    background = Color(0xFFFCFDFF),
    secondary = Color(0xFFF9FCFF),
    onSecondary = Color(0xFF1E569E),
    tertiary = Pink40,
    onSurface = Color(0xFF001227),
    outline = Color(0xFF001227).copy(alpha = 0.75f),
    onSurfaceVariant = Color(0xFF001227).copy(alpha = 0.75f)
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun InzProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> CustomDarkColorScheme
        else -> CustomLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}