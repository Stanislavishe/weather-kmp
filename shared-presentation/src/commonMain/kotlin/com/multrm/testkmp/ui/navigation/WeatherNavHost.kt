package com.multrm.testkmp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.multrm.testkmp.ui.weatherScreen.WeatherScreen
import com.multrm.testkmp.ui.homeScreen.HomeScreen

@Composable
fun WeatherNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen,
        modifier = Modifier
            .padding(innerPadding)
    ) {
        composable<Routes.HomeScreen> {
            HomeScreen { city ->
                navController.navigate(Routes.WeatherScreen(city))
            }
        }
        composable<Routes.WeatherScreen> {
            val city = it.toRoute<Routes.WeatherScreen>().city
            WeatherScreen(
                cityName = city
            )
        }
    }
}