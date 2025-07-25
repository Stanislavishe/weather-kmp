package com.multrm.testkmp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.multrm.testkmp.ui.navigation.WeatherNavHost
import com.multrm.testkmp.ui.theme.WeatherTheme
import org.koin.compose.KoinContext

@Composable
fun WeatherApp(): Unit = KoinContext {
    val navController = rememberNavController()

    WeatherTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            WeatherNavHost(navController, innerPadding)
        }
    }
}