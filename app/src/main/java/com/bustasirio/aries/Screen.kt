package com.bustasirio.aries

sealed class Screen(val route: String) {
    object HomeScreen: Screen("aries_home")
    object ApodDetailScreen: Screen("aries_apod_detail")
    object SavedApodsScreen: Screen("aries_saved_apods")
}