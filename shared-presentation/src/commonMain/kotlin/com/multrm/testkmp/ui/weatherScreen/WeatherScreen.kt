package com.multrm.testkmp.ui.weatherScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multrm.testkmp.ui.weatherScreen.components.ErrorBox
import com.multrm.testkmp.ui.weatherScreen.components.ItemForecastInfo
import com.multrm.testkmp.ui.weatherScreen.components.ItemWeatherInfo
import com.multrm.testkmp.ui.weatherScreen.components.WeatherIcon
import com.multrm.testkmp.ui.LoadingState
import com.multrm.testkmp.ui.events.ImHereEvent
import com.multrm.testkmp.ui.utils.PlatformLogger
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import weatherkmp.shared_presentation.generated.resources.ErrorText_NoInternet
import weatherkmp.shared_presentation.generated.resources.ErrorText_NoVPN
import weatherkmp.shared_presentation.generated.resources.ErrorText_NotFound
import weatherkmp.shared_presentation.generated.resources.ErrorText_SometingWentWrong
import weatherkmp.shared_presentation.generated.resources.Res
import weatherkmp.shared_presentation.generated.resources.WeatherScreen_FerecastFewDays
import weatherkmp.shared_presentation.generated.resources.WeatherScreen_ImHere
import weatherkmp.shared_presentation.generated.resources.WeatherScreen_Title
import kotlin.math.log

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    cityName: String,
    vm: WeatherVM = koinViewModel(),
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val hereEventState by vm.hereEventState.collectAsStateWithLifecycle()
    val logger = remember { PlatformLogger() }
    LaunchedEffect(Unit) {
        vm.getWeather(cityName)
        vm.getImHereCity()
    }
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            stringResource(Res.string.WeatherScreen_Title),
            fontSize = 32.sp
        )
        Text(
            cityName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        when (val state = uiState) {
            WeatherUiState.Error.NotFoundCity -> {
                ErrorBox(
                    errorText = stringResource(Res.string.ErrorText_NotFound),
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { vm.getWeather(cityName) }
            }

            WeatherUiState.Error.Other -> {
                ErrorBox(
                    errorText = stringResource(Res.string.ErrorText_SometingWentWrong), Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { vm.getWeather(cityName) }
            }

            WeatherUiState.Error.UnknownHost -> {
                ErrorBox(
                    errorText = stringResource(Res.string.ErrorText_NoInternet),
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { vm.getWeather(cityName) }
            }

            WeatherUiState.Error.Timeout -> {
                ErrorBox(
                    errorText = stringResource(Res.string.ErrorText_NoVPN),
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { vm.getWeather(cityName) }
            }

            WeatherUiState.Initial -> {}
            WeatherUiState.Loading -> {
                LoadingState()
            }

            is WeatherUiState.Success -> {
                WeatherContent(state)
                Spacer(Modifier.height(8.dp))
                when (hereEventState) {
                    is ImHereEvent.HereCity -> logger.logDebug("WeatherScreen", "HereCity")
                    ImHereEvent.Initial -> logger.logDebug("WeatherScreen", "Initial")
                    ImHereEvent.Loading -> logger.logDebug("WeatherScreen", "Loading")
                    ImHereEvent.NotHere -> logger.logDebug("WeatherScreen", "NotHere")
                }
                val isHereCity = hereEventState is ImHereEvent.HereCity
                if (isHereCity) {
                    Button(
                        onClick = { vm.setImHere(null) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = hereEventState !is ImHereEvent.Loading,
                    ) {
                        Text(
                            text = stringResource(Res.string.WeatherScreen_ImHere),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = { vm.setImHere(cityName) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = hereEventState !is ImHereEvent.Loading,
                    ) {
                        Text(
                            text = stringResource(Res.string.WeatherScreen_ImHere),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
//                Button(
//                    onClick = {
//                        vm.setImHere(
//                            if (hereEventState is ImHereEvent.HereCity) null
//                            else cityName
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth(),
//                    enabled = hereEventState !is ImHereEvent.Loading,
//                    shape = if (isHereCity) ButtonDefaults.shape else ButtonDefaults.outlinedShape,
//                    colors = if (isHereCity) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
//                    elevation = if (isHereCity) ButtonDefaults.buttonElevation() else null,
//                    border = if (isHereCity) null else ButtonDefaults.outlinedButtonBorder(hereEventState !is ImHereEvent.Loading),
//                    contentPadding = ButtonDefaults.ContentPadding,
//                    interactionSource = if (isHereCity),
//                ) {
//
//                }
            }
        }
    }
}

@Composable
private fun WeatherContent(state: WeatherUiState.Success) {
    Column(Modifier.heightIn(max = 5000.dp)) {
        Spacer(Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = state.current.weatherState,
                fontSize = 28.sp
            )
            WeatherIcon(
                Modifier
                    .padding(start = 4.dp)
                    .size(25.dp), state.current.weatherIcon
            )
        }
        Spacer(Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            val gridList = state.current.infoList.toMutableList()
            gridList.removeAt(gridList.lastIndex)
            items(gridList, key = { it.titleResId }) { item ->
                ItemWeatherInfo(item, false)
            }
        }
        Spacer(Modifier.height(6.dp))
        ItemWeatherInfo(state.current.infoList.last(), true)
        Spacer(Modifier.height(12.dp))
        Text(
            stringResource(Res.string.WeatherScreen_FerecastFewDays),
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(state.forecast) {
                ItemForecastInfo(it)
            }
        }
    }
}

@Preview
@Composable
private fun WeatherScreenPreview() {
//    WeatherScreen()
}