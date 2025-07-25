package com.multrm.testkmp.di

import com.multrm.shared.domain.Operation
import com.multrm.testkmp.ui.homeScreen.HomeVM
import com.multrm.testkmp.ui.weatherScreen.WeatherUiMapper
import com.multrm.testkmp.ui.weatherScreen.WeatherUiState
import com.multrm.testkmp.ui.weatherScreen.WeatherVM
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val vmModule = module {
    single<Operation.Mapper<WeatherUiState>> {
        WeatherUiMapper()
    }
    viewModelOf(::HomeVM)
    viewModelOf(::WeatherVM)
}