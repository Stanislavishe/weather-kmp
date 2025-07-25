package com.multrm.shared.domain.db

import com.multrm.shared.domain.weatherScreenEntity.CurrentInfo
import com.multrm.shared.domain.weatherScreenEntity.ItemForecast

data class CityWeatherFromDb(
    val name: String,
    val current: CurrentInfo?,
    val forecast: List<ItemForecast>?
)
