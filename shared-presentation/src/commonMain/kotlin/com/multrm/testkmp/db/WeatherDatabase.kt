package com.multrm.testkmp.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.multrm.shared.domain.WeatherDao
import com.multrm.shared.domain.db.CityWeatherFrommDbRemote
import com.multrm.shared.domain.db.ImHereCityDb

@Database(
    entities = [
        CityWeatherFrommDbRemote::class,
        ImHereCityDb::class
    ], version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<WeatherDatabase> {
    override fun initialize(): WeatherDatabase
}