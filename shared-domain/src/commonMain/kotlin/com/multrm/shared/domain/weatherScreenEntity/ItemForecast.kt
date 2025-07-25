package com.multrm.shared.domain.weatherScreenEntity
import kotlinx.serialization.Serializable

@Serializable
data class ItemForecast(
    val date: String,
    val icon: String,
    val temp: Temperature
) {

    @Serializable
    data class Temperature(
        val resId: String,
        val minTemp: Int,
        val maxTemp: Int
    )
}
