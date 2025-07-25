package com.multrm.shared.domain.weatherScreenEntity

import kotlinx.serialization.Serializable

@Serializable
data class CurrentInfo(
    val weatherState: String,
    val weatherIcon: String,
    val infoList: List<ItemInfo>
)
