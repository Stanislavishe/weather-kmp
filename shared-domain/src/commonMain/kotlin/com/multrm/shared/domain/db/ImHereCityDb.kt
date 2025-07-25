package com.multrm.shared.domain.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "im_here")
data class ImHereCityDb(
    @PrimaryKey
    val id: Int = 1,
    val imHere: String? = null
)
