package com.multrm.testkmp.ui.weatherScreen

import com.multrm.shared.domain.Operation
import com.multrm.shared.domain.db.CityWeatherFromDb
import com.multrm.shared.domain.models.CityWeather
import com.multrm.shared.domain.weatherScreenEntity.CurrentInfo
import com.multrm.shared.domain.weatherScreenEntity.ItemForecast
import com.multrm.shared.domain.weatherScreenEntity.ItemInfo
import io.ktor.client.network.sockets.SocketTimeoutException
import weatherkmp.shared_presentation.generated.resources.Res
import weatherkmp.shared_presentation.generated.resources.c
import weatherkmp.shared_presentation.generated.resources.dewPoint
import weatherkmp.shared_presentation.generated.resources.dir_e
import weatherkmp.shared_presentation.generated.resources.dir_ene
import weatherkmp.shared_presentation.generated.resources.dir_ese
import weatherkmp.shared_presentation.generated.resources.dir_n
import weatherkmp.shared_presentation.generated.resources.dir_ne
import weatherkmp.shared_presentation.generated.resources.dir_nee
import weatherkmp.shared_presentation.generated.resources.dir_nnw
import weatherkmp.shared_presentation.generated.resources.dir_nw
import weatherkmp.shared_presentation.generated.resources.dir_s
import weatherkmp.shared_presentation.generated.resources.dir_se
import weatherkmp.shared_presentation.generated.resources.dir_ssn
import weatherkmp.shared_presentation.generated.resources.dir_ssw
import weatherkmp.shared_presentation.generated.resources.dir_sw
import weatherkmp.shared_presentation.generated.resources.dir_w
import weatherkmp.shared_presentation.generated.resources.dir_wnw
import weatherkmp.shared_presentation.generated.resources.dir_wsw
import weatherkmp.shared_presentation.generated.resources.direction
import weatherkmp.shared_presentation.generated.resources.filslike
import weatherkmp.shared_presentation.generated.resources.forecastTemp
import weatherkmp.shared_presentation.generated.resources.gustsWind
import weatherkmp.shared_presentation.generated.resources.humidity
import weatherkmp.shared_presentation.generated.resources.kph
import weatherkmp.shared_presentation.generated.resources.mbar
import weatherkmp.shared_presentation.generated.resources.pressure
import weatherkmp.shared_presentation.generated.resources.procent
import weatherkmp.shared_presentation.generated.resources.temperature
import weatherkmp.shared_presentation.generated.resources.unknown_dir
import weatherkmp.shared_presentation.generated.resources.uvIndex
import weatherkmp.shared_presentation.generated.resources.wind

class WeatherUiMapper : Operation.Mapper<WeatherUiState> {
    override fun <T> mapSuccess(data: T): WeatherUiState {
        return if (data is CityWeather) {
            val weatherState = data.current.condition.text
            val weatherIcon = "https:" + data.current.condition.icon
            val currentList = buildCurrentList(data.current)

            val currentInfo = CurrentInfo(weatherState, weatherIcon, currentList)
            val forecastList = buildList {
                data.forecast.forecastday.forEach { forecast ->
                    val convertedDate = forecast.date.takeLast(5).replace("-", ".")
                    val icon = "https:" + forecast.day.condition.icon
                    val temp = ItemForecast.Temperature(
                        Res.string.forecastTemp.key,
                        forecast.day.minTemp.toInt(),
                        forecast.day.maxTemp.toInt()
                    )
                    add(ItemForecast(convertedDate, icon, temp))
                }
            }

            WeatherUiState.Success(currentInfo, forecastList)
        } else if (
            (data as? List<CityWeatherFromDb>)?.lastOrNull()?.forecast != null &&
            (data as? List<CityWeatherFromDb>)?.lastOrNull()?.current != null
        ) {
            data.last().let { WeatherUiState.Success(it.current!!, it.forecast!!) }
        } else {
            WeatherUiState.Error.UnknownHost
        }
    }

    override fun mapError(exception: Exception): WeatherUiState {
        return when (exception) {
            is IllegalArgumentException -> WeatherUiState.Error.NotFoundCity
            is SocketTimeoutException -> WeatherUiState.Error.Timeout
            else -> {
                if (isUnknownHostException(exception)) WeatherUiState.Error.UnknownHost
                else WeatherUiState.Error.Other.also { exception.printStackTrace() }
            }
        }
    }

    private fun convertWindDir(windDir: String): String {
        return when (windDir) {
            "S" -> Res.string.dir_s.key
            "W" -> Res.string.dir_w.key
            "N" -> Res.string.dir_n.key
            "E" -> Res.string.dir_e.key
            "SW" -> Res.string.dir_sw.key
            "SE" -> Res.string.dir_se.key
            "NW" -> Res.string.dir_nw.key
            "NE" -> Res.string.dir_ne.key
            "SSW" -> Res.string.dir_ssw.key
            "SSN" -> Res.string.dir_ssn.key
            "WSW" -> Res.string.dir_wsw.key
            "NEE" -> Res.string.dir_nee.key
            "ENE" -> Res.string.dir_ene.key
            "ESE" -> Res.string.dir_ese.key
            "WNW" -> Res.string.dir_wnw.key
            "NNW" -> Res.string.dir_nnw.key
            else -> {
                Res.string.unknown_dir.key
            }
        }
    }

    private fun buildCurrentList(current: CityWeather.Current) = buildList {
        add(
            ItemInfo(
                titleResId = Res.string.temperature.key,
                value = current.tempC.toInt().toString(),
                measurementResId = Res.string.c.key
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.filslike.key,
                value = current.feelsLikeC.toInt().toString(),
                measurementResId = Res.string.c.key
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.wind.key,
                value = current.winKph.toInt().toString(),
                measurementResId = Res.string.kph.key
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.gustsWind.key,
                value = current.gustKph.toInt().toString(),
                measurementResId = Res.string.kph.key
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.pressure.key,
                value = current.pressureMb.toInt().toString(),
                measurementResId = Res.string.mbar.key
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.humidity.key,
                value = current.humidity.toString(),
                measurementResId = Res.string.procent.key
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.dewPoint.key,
                value = current.dewPointC.toInt().toString(),
                measurementResId = Res.string.c.key
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.uvIndex.key,
                value = current.uv.toInt().toString(),
                measurementResId = "0"
            )
        )
        add(
            ItemInfo(
                titleResId = Res.string.direction.key,
                value = convertWindDir(current.windDir),
                measurementResId = "0"
            )
        )
    }
}
expect fun isUnknownHostException(e: Exception): Boolean
