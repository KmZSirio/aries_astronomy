package com.bustasirio.aries.feature_apod.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.bustasirio.aries.feature_apod.domain.model.Apod
import com.bustasirio.aries.feature_apod.presentation.apod_detail.ApodDetailScreen
import com.bustasirio.aries.feature_apod.presentation.home.HomeScreen
import com.bustasirio.aries.feature_apod.presentation.ui.theme.AriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AriesTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route
                ) {
                    composable(Screen.HomeScreen.route) {
                        HomeScreen(navController = navController)
                    }
                    composable(Screen.ApodDetailScreen.route) { backStackEntry ->
//                        val apod = backStackEntry.savedStateHandle.get<Apod>("apod")
                        val apod =
                            navController.previousBackStackEntry?.savedStateHandle?.get<Apod>("apod")
                        apod?.let {
                            ApodDetailScreen(navController = navController, apod = it)
                        }
                    }
                }
            }
        }
    }
}