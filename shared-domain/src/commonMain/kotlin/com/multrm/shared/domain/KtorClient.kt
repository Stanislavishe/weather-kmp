package com.multrm.shared.domain

import com.multrm.shared.domain.models.CityWeather

interface KtorClient {
    suspend fun getWeather(cityName: String, days: Int): Operation<CityWeather>
}