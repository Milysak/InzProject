package com.example.inzproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.inzproject.screens.AnimatedSplashScreen
import com.example.inzproject.screens.BottomNavigation
import com.example.inzproject.viewmodels.MainViewModel

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Routes.Splash.route) {
        composable(route = Routes.Splash.route) {
            AnimatedSplashScreen(navHostController)
        }

        composable(route = Routes.Main.route) {
            BottomNavigation()
        }
    }
}