package com.multrm.testkmp.android

import android.app.Application
import com.multrm.testkmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(androidContext = this@WeatherApp)
            androidLogger(level = Level.INFO)
        }
    }
}