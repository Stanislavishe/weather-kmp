package com.multrm.shared.domain.db.converters

import com.multrm.shared.domain.weatherScreenEntity.CurrentInfo
import kotlinx.serialization.json.Json

object CurrentInfoConverter {
    fun infoToJson(info: CurrentInfo?): String? {
        return info?.let {
            Json.encodeToString(info)
        }
    }
    fun jsonToInfo(json: String?): CurrentInfo? {
        return json?.let {
            Json.decodeFromString(json)
        }
    }
}