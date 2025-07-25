package com.multrm.shared.domain.db.converters

import com.multrm.shared.domain.weatherScreenEntity.ItemForecast
import kotlinx.serialization.json.Json

object ItemForecastConverter {
    fun listToJson(list: List<ItemForecast>?): String? {
        return list?.let {
            Json.encodeToString(list)
        }
    }

    fun jsonToList(json: String?): List<ItemForecast>? {
        return json?.let {
            Json.decodeFromString(json)
        }
    }
}