package com.multrm.testkmp.di

import androidx.room.Room
import com.multrm.shared.domain.WeatherDao
import com.multrm.testkmp.db.WeatherDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope
import org.koin.dsl.module

val dbModule = module {
    single<WeatherDatabase> {
//        Room.databaseBuilder(
//            androidContext(), WeatherDatabase::class.java, "Weather Database"
//        ).fallbackToDestructiveMigration().build()
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath("Weather Database")
        Room.databaseBuilder<WeatherDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
            .fallbackToDestructiveMigrationOnDowngrade()
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single<WeatherDao> {
        get<WeatherDatabase>().weatherDao()
    }
}
