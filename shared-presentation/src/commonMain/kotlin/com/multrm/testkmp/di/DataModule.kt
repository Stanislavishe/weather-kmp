package com.multrm.testkmp.di

import com.multrm.shared.domain.WeatherDbRepository
import com.multrm.shared.data.KtorClientImpl
import com.multrm.shared.data.WeatherDbRepositoryImpl
import com.multrm.shared.data.WeatherRepositoryImpl
import com.multrm.shared.domain.KtorClient
import com.multrm.shared.domain.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val dataModule = module {
    single {
        HttpClient {
            defaultRequest { url("https://api.weatherapi.com/v1/") }
            install(HttpCache)
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }

    single<KtorClient> {
        KtorClientImpl(client = get())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(ktorClient = get())
    }

    single<WeatherDbRepository> {
        WeatherDbRepositoryImpl(dao = get())
    }
}