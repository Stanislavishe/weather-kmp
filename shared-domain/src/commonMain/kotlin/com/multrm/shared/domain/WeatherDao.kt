package com.multrm.shared.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.multrm.shared.domain.db.CityWeatherFrommDbRemote
import com.multrm.shared.domain.db.ImHereCityDb

@Dao
interface WeatherDao {
    @Query("SELECT * FROM city_weather WHERE name = :name")
    fun getWeatherInfo(name: String): List<CityWeatherFrommDbRemote>

    @Query("SELECT * FROM im_here")
    fun getImHereCity() : List<ImHereCityDb>

    @Insert(onConflict = REPLACE)
    fun insertWeatherInfo(cityWeatherFrommDb: CityWeatherFrommDbRemote)

    @Insert(onConflict = REPLACE)
    fun insertImHereCity(imHereDb: ImHereCityDb)
}