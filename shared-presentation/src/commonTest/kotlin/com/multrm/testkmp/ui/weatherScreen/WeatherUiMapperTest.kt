package com.multrm.testkmp.ui.weatherScreen

import com.multrm.shared.domain.db.CityWeatherFromDb
import com.multrm.shared.domain.models.CityWeather
import com.multrm.shared.domain.weatherScreenEntity.CurrentInfo
import com.multrm.shared.domain.weatherScreenEntity.ItemForecast
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlin.test.Test
import kotlin.test.assertTrue

class WeatherUiMapperTest {
    private val fakeCityWeather = CityWeather(
        location = CityWeather.Location(
            country = "", lat = 0.0, lon = 0.0, localtime = "", localtimeEpoch = 0,
            name = "", region = "", tzId = ""
        ),
        current = CityWeather.Current(
            condition = CityWeather.Condition(
                code = 0, icon = "", text = ""
            ),
            feelsLikeC = 0.0, gustKph = 0.0, humidity = 0, lastUpdatedEpoch = 0,
            pressureMb = 0.0, tempC = 0.0, uv = 0.0, winKph = 0.0, windDir = "", dewPointC = 0.0
        ),
        forecast = CityWeather.ForecastDay(
            listOf(
                CityWeather.ForecastDay.ForecastDayItem(
                    date = "",
                    CityWeather.ForecastDay.ForecastDayItem.Day(
                        maxTemp = 0.0,
                        0.0,
                        CityWeather.Condition(
                            code = 0, icon = "", text = ""
                        )
                    )
                )
            )
        )
    )

    @Test
    fun `test success try is CityWeather`() {
        val mapper = WeatherUiMapper()
        val uiState = mapper.mapSuccess(fakeCityWeather)
        assertTrue(uiState is WeatherUiState.Success)
    }

    @Test
    fun `test success try is CityWeatherFromDb`() {
        val mapper = WeatherUiMapper()
        val fakeListCityWeatherFromDb = listOf(
            CityWeatherFromDb(
                name = "", current = CurrentInfo(
                    weatherState = "", weatherIcon = "", infoList = listOf()
                ),
                forecast = listOf(ItemForecast(
                    date = "", icon = "", ItemForecast.Temperature("0", 0, 0)
                ))
            )
        )

        val uiState = mapper.mapSuccess(fakeListCityWeatherFromDb)
        assertTrue(uiState is WeatherUiState.Success)
    }

    @Test
    fun `test error try is empty CityWeatherFromDb`() {
        val mapper = WeatherUiMapper()
        val fakeListCityWeatherFromDb = listOf(
            CityWeatherFromDb(
                name = "", current = null,
                forecast = null
            )
        )

        val uiState = mapper.mapSuccess(fakeListCityWeatherFromDb)
        assertTrue(uiState is WeatherUiState.Error.UnknownHost)
    }

    @Test
    fun `test mapError's`() {
        val mapper = WeatherUiMapper()
        var exception: Exception
        var uiState: WeatherUiState

        exception = IllegalArgumentException()
        uiState = mapper.mapError(exception)
        assertTrue(uiState is WeatherUiState.Error.NotFoundCity)

        exception = SocketTimeoutException("")
        uiState = mapper.mapError(exception)
        assertTrue(uiState is WeatherUiState.Error.Timeout)


        exception = Exception()
        uiState = mapper.mapError(exception)
        assertTrue(uiState is WeatherUiState.Error.Other)
    }
}