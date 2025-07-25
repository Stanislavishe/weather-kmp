package com.multrm.shared.data

import com.multrm.shared.domain.WeatherDbRepository
import com.multrm.shared.domain.Operation
import com.multrm.shared.domain.WeatherDao
import com.multrm.shared.domain.db.CityWeatherFromDb
import com.multrm.shared.domain.db.CityWeatherFrommDbRemote
import com.multrm.shared.domain.db.ImHereCityDb
import com.multrm.shared.domain.db.converters.CurrentInfoConverter
import com.multrm.shared.domain.db.converters.ItemForecastConverter
import com.multrm.shared.domain.db.toDomainCityWeatherFrommDb
import com.multrm.shared.domain.weatherScreenEntity.CurrentInfo
import com.multrm.shared.domain.weatherScreenEntity.ItemForecast

class WeatherDbRepositoryImpl(
    private val dao: WeatherDao,
) : WeatherDbRepository {
    override suspend fun setImHere(value: String?): Boolean {
        return try {
            dao.insertImHereCity(ImHereCityDb(imHere = value))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    override suspend fun getImHereCity(): String? {
        return try {
            dao.getImHereCity().last().imHere
        } catch (_: Exception) {
            null
        }
    }


    override suspend fun getCacheWeather(name: String): Operation<List<CityWeatherFromDb>> {
        return try {
            val data = dao.getWeatherInfo(name)
            Operation.Success(data.map { it.toDomainCityWeatherFrommDb() })
        } catch (e: Exception) {
            e.printStackTrace()
            Operation.Failure(e)
        }
    }

    override suspend fun putCacheWeather(
        name: String,
        current: CurrentInfo,
        forecast: List<ItemForecast>,
    ) {
        val weather = CityWeatherFrommDbRemote(
            current = CurrentInfoConverter.infoToJson(current),
            forecast = ItemForecastConverter.listToJson(forecast),
            name = name
        )
        dao.insertWeatherInfo(weather)
    }
}