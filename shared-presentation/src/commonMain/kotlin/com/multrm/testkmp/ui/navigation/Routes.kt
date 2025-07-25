package com.multrm.testkmp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object HomeScreen: Routes()

    @Serializable
    data class WeatherScreen(val city: String): Routes()
}