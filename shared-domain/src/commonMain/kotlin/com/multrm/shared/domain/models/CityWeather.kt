package com.multrm.shared.domain.models

data class CityWeather(
    val location: Location,
    val current: Current,
    val forecast: ForecastDay
) {
    data class Location(
        val country: String,
        val lat: Double,
        val localtime: String,
        val localtimeEpoch: Int,
        val lon: Double,
        val name: String,
        val region: String,
        val tzId: String
    )

    data class Current(
        val condition: Condition,
        val feelsLikeC: Double,
        val gustKph: Double,
        val humidity: Int,
        val lastUpdatedEpoch: Int,
        val pressureMb: Double,
        val tempC: Double,
        val uv: Double,
        val winKph: Double,
        val windDir: String,
        val dewPointC: Double
    )

    data class ForecastDay(
        val forecastday: List<ForecastDayItem>
    ){
        data class ForecastDayItem(
            val date: String,
            val day: Day
        ) {
            data class Day(
                val maxTemp: Double,
                val minTemp: Double,
                val condition: Condition
            )
        }
    }

    data class Condition(
        val code: Int,
        val icon: String,
        val text: String
    )
}