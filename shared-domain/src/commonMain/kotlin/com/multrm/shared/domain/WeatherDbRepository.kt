package com.multrm.shared.domain

import com.multrm.shared.domain.db.CityWeatherFromDb
import com.multrm.shared.domain.db.CityWeatherFrommDbRemote
import com.multrm.shared.domain.weatherScreenEntity.CurrentInfo
import com.multrm.shared.domain.weatherScreenEntity.ItemForecast

interface WeatherDbRepository {
    suspend fun setImHere(value: String?): Boolean
    suspend fun getImHereCity(): String?

    suspend fun getCacheWeather(name: String): Operation<List<CityWeatherFromDb>>
    suspend fun putCacheWeather(
        name: String,
        current: CurrentInfo,
        forecast: List<ItemForecast>
    )
}