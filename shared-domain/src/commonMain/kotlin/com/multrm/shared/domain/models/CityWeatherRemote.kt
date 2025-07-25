package com.multrm.shared.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CityWeatherRemote(
    val location: Location,
    val current: Current,
    val forecast: ForecastDay
) {
    @Serializable
    data class Location(
        val country: String,
        val lat: Double,
        val localtime: String,
        val localtime_epoch: Int,
        val lon: Double,
        val name: String,
        val region: String,
        val tz_id: String
    )

    @Serializable
    data class Current(
        val condition: Condition,
        val feelslike_c: Double,
        val gust_kph: Double,
        val humidity: Int,
        val last_updated_epoch: Int,
        val pressure_mb: Double,
        val temp_c: Double,
        val uv: Double,
        val wind_kph: Double,
        val wind_dir: String,
        val dewpoint_c: Double
    )

    @Serializable
    data class ForecastDay(
        val forecastday: List<ForecastDayItem>
    ) {
        @Serializable
        data class ForecastDayItem(
            val date: String,
            val day: Day
        ) {
            @Serializable
            data class Day(
                val maxtemp_c: Double,
                val mintemp_c: Double,
                val condition: Condition
            )
        }
    }

    @Serializable
    data class Condition(
        val code: Int,
        val icon: String,
        val text: String
    )
}

fun CityWeatherRemote.toDomainCityWeather() = CityWeather(
    CityWeather.Location(
        country = this.location.country,
        lat = this.location.lat,
        localtime = this.location.localtime,
        localtimeEpoch = this.location.localtime_epoch,
        lon = this.location.lon,
        name = this.location.name,
        region = this.location.region,
        tzId = this.location.tz_id
    ),
    current = CityWeather.Current(
        condition = CityWeather.Condition(
            code = this.current.condition.code,
            icon = this.current.condition.icon,
            text = this.current.condition.text
        ),
        feelsLikeC = this.current.feelslike_c,
        gustKph = this.current.gust_kph,
        humidity = this.current.humidity,
        lastUpdatedEpoch = this.current.last_updated_epoch,
        pressureMb = this.current.pressure_mb,
        tempC = this.current.temp_c,
        uv = this.current.uv,
        winKph = this.current.wind_kph,
        windDir = this.current.wind_dir,
        dewPointC = this.current.dewpoint_c
    ),
    forecast = CityWeather.ForecastDay(
        this.forecast.forecastday.map {
            CityWeather.ForecastDay.ForecastDayItem(
                date = it.date,
                day = CityWeather.ForecastDay.ForecastDayItem.Day(
                    maxTemp = it.day.maxtemp_c,
                    minTemp = it.day.mintemp_c,
                    condition = CityWeather.Condition(
                        code = it.day.condition.code,
                        icon = it.day.condition.icon,
                        text = it.day.condition.text
                    )
                )
            )
        }
    )
)
