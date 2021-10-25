package com.bustasirio.aries.feature_apod.presentation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("aries_home")
    object ApodDetailScreen: Screen("aries_apod_detail")
}