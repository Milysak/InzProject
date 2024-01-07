package com.example.inzproject.navigation

import com.example.inzproject.R

sealed class BottomRoutes(val route: String, val filledIcon: Int, val outlinedIcon: Int) {
    object Home : BottomRoutes("home_screen", R.drawable.filled_home, R.drawable.outlined_home)
    object LikeToVisit : BottomRoutes("like_to_visit_screen", R.drawable.filled_heart, R.drawable.outlined_heart)
    object Map : BottomRoutes("map_screen", R.drawable.filled_world, R.drawable.outlined_world)
    object Compass : BottomRoutes("compass_screen", R.drawable.filled_compass, R.drawable.outlined_compass)
    /*object Settings : BottomRoutes("settings_screen", R.drawable.filled_settings, R.drawable.outlined_settings)*/
}