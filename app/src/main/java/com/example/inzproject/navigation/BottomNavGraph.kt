package com.example.inzproject.navigation

import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.inzproject.screens.CompassScreen
import com.example.inzproject.screens.HomeScreen
import com.example.inzproject.screens.LikeToVisitScreen
import com.example.inzproject.screens.MapScreen
import com.example.inzproject.viewmodels.MainViewModel

@Composable
fun BottomNavGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = BottomRoutes.Home.route) {
        composable(route = BottomRoutes.Home.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                HomeScreen()
                //

            }
        }

        composable(route = BottomRoutes.LikeToVisit.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center,) {
                LikeToVisitScreen()
            }
        }

        composable(route = BottomRoutes.Map.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MapScreen()
            }
        }

        composable(route = BottomRoutes.Compass.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CompassScreen()
            }
        }

        composable(route = BottomRoutes.Settings.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "SETTINGS")
            }
        }
    }
}