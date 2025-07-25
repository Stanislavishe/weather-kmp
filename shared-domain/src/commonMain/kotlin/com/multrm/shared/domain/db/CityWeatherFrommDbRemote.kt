package com.multrm.shared.domain.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.multrm.shared.domain.db.converters.CurrentInfoConverter
import com.multrm.shared.domain.db.converters.ItemForecastConverter
import com.multrm.shared.domain.weatherScreenEntity.CurrentInfo
import com.multrm.shared.domain.weatherScreenEntity.ItemForecast

@Entity(tableName = "city_weather")
data class CityWeatherFrommDbRemote(
    @PrimaryKey
    val name: String,
    val current: String? = null, // CurrentInfo
    val forecast: String? = null // List<ItemForecast>
)

fun CityWeatherFrommDbRemote.toDomainCityWeatherFrommDb(): CityWeatherFromDb =
    CityWeatherFromDb(
        name = this.name,
        current = CurrentInfoConverter.jsonToInfo(this.current),
        forecast = ItemForecastConverter.jsonToList(this.forecast)
    )

