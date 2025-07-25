package com.multrm.testkmp.di

import com.multrm.testkmp.ui.weatherScreen.DispatchersList
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

internal val appModule = module {
    single<DispatchersList> {
        DispatchersList.Base()
    }
}