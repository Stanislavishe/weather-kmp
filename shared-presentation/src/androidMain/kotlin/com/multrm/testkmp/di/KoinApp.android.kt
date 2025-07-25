package com.multrm.testkmp.di

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

actual fun initKoin(config: KoinAppDeclaration?) {
    startKoin {
//        printLogger()
        includes(configurations = arrayOf(config))
        modules(modules = appModule + dbModule + dataModule + vmModule)
    }
}