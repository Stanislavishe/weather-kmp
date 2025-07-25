package com.multrm.shared.domain.weatherScreenEntity

import kotlinx.serialization.Serializable

@Serializable
data class ItemInfo(
    val titleResId: String,
    val value: String,
    val measurementResId: String
)
