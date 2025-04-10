package com.rafael0112.asessment1.navigation

sealed class Screen( val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}