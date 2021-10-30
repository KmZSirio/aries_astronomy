package com.bustasirio.aries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Savings
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.presentation.apod_detail.ApodDetailScreen
import com.bustasirio.aries.feature_apod.presentation.home.HomeScreen
import com.bustasirio.aries.feature_apod.presentation.saved_apods.SavedApodsScreen
import com.bustasirio.aries.feature_navbar.BottomNavItem
import com.bustasirio.aries.feature_navbar.BottomNavigationBar
import com.bustasirio.aries.ui.theme.AriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AriesTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        CompositionLocalProvider(LocalElevationOverlay provides null) {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route =  Screen.HomeScreen.route,
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = "Saved",
                                        route =  Screen.SavedApodsScreen.route,
                                        icon = Icons.Default.Favorite
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route) {
                                        this.popUpTo(it.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route
                        ) {
                            composable(Screen.HomeScreen.route) {
                                HomeScreen(navController = navController)
                            }
                            composable(
                                route = Screen.ApodDetailScreen.route + "?date={date}",
                                arguments = listOf(
                                    navArgument(name = "date") { NavType.StringType }
                                )) {
                                val apod =
                                    navController.previousBackStackEntry?.savedStateHandle?.get<Apod>("apod")
                                apod?.let {
                                    ApodDetailScreen(apod = it)
                                }
                            }
                            composable(Screen.SavedApodsScreen.route) {
                                SavedApodsScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}