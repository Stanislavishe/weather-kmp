package com.multrm.shared.domain

import com.multrm.shared.domain.models.CityWeather

interface WeatherRepository {
    suspend fun getCitiesList(): List<String>
    suspend fun getWeather(cityName: String, days: Int): Operation<CityWeather>
}