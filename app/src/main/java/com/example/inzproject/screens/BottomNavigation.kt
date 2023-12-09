package com.example.inzproject.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.inzproject.helpfunctions.createGradientBrush
import com.example.inzproject.navigation.BottomNavGraph
import com.example.inzproject.navigation.BottomRoutes
import com.example.inzproject.navigation.Routes
import com.example.inzproject.viewmodels.MainViewModel

@Composable
fun BottomNavigation(mainViewModel: MainViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier
            .padding(innerPadding)
            .background(
                createGradientBrush(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.85f)
                    )
                )
            ),
        ) {
            BottomNavGraph(navHostController = navController, mainViewModel = mainViewModel)
        }

    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val density = LocalDensity.current.density

    val screens = listOf(
        BottomRoutes.Home,
        BottomRoutes.LikeToVisit,
        BottomRoutes.Map,
        BottomRoutes.Compass,
        BottomRoutes.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var offSet by remember { mutableStateOf(-100.0f) }
    val offSetX by animateFloatAsState(
        targetValue = offSet,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Column {

        Row(
            modifier = Modifier
                .background(
                    createGradientBrush(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.125f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .fillMaxWidth()
                .height(3.dp)
        ) {

            /*Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .clip(CircleShape)
            .size(offSetX.dp, 3.dp)
            .offset(offSetX.dp, 0.dp)
        ) {  }*/

        }

        Row(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 2.5.dp, bottom = 0.dp)
                .background(Color.Transparent)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true

                val contentColor by animateColorAsState(
                    targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.55f
                    ),
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )

                Box(
                    modifier = Modifier
                        .height(55.dp)
                        .clip(CircleShape)
                        .clickable(interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (currentDestination?.hierarchy?.any { it.route == screen.route } == false) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 14.dp, bottom = 0.dp)
                            .scale(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Icon(
                            painter = painterResource(id = if (selected) screen.filledIcon else screen.outlinedIcon),
                            contentDescription = "icon",
                            tint = contentColor,
                            modifier = Modifier
                                .onGloballyPositioned { coords ->
                                    if (selected) {
                                        //offSet = coords.positionInRoot().x / density + coords.size.width / density / 2
                                        offSet = coords.positionInRoot().x + coords.size.width / 2
                                    }
                                }
                                .scale(0.95f)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
        ) {

            /*Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .clip(CircleShape)
            .size(offSetX.dp, 3.dp)
            .offset(offSetX.dp, 0.dp)
        ) {  }*/

            Layout(content = {
                Box(
                    modifier = Modifier
                        .background(
                            createGradientBrush(
                                listOf(
                                    MaterialTheme.colorScheme.background,
                                    MaterialTheme.colorScheme.primary
                                ), false
                            )
                        )
                        .clip(CircleShape)
                        .size(35.dp, 2.dp)
                )
                Box(
                    modifier = Modifier
                        .background(
                            createGradientBrush(
                                listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.background
                                ), false
                            )
                        )
                        .clip(CircleShape)
                        .size(35.dp, 2.dp)
                )
            }
            ) { measurables, constrainsts ->
                val indicator1 = measurables[0].measure(constrainsts)
                val indicator2 = measurables[1].measure(constrainsts)

                layout(constrainsts.maxWidth, constrainsts.maxHeight) {
                    indicator1.place(offSetX.toInt() - 105, 0)
                    indicator2.place(offSetX.toInt(), 0)
                }
            }

        }
    }
}