package com.multrm.testkmp.ui.weatherScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multrm.shared.domain.WeatherDbRepository
import com.multrm.shared.domain.Operation
import com.multrm.shared.domain.WeatherRepository
import com.multrm.testkmp.ui.events.ImHereEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherVM(
    private val repository: WeatherRepository,
    private val dbRepository: WeatherDbRepository,
    private val mapper: Operation.Mapper<WeatherUiState>,
    private val dispatchers: DispatchersList,
) : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _hereEventState = MutableStateFlow<ImHereEvent>(ImHereEvent.Initial)
    val hereEventState = _hereEventState.asStateFlow()

    fun getWeather(cityName: String) = viewModelScope.launch(dispatchers.ui()) {
        _uiState.value = WeatherUiState.Loading
        val state = repository.getWeather(cityName, 4).map(mapper)
        when (state) {
            is WeatherUiState.Success -> {
                try {
                    withContext(dispatchers.io()) {
                        dbRepository.putCacheWeather(cityName, state.current, state.forecast)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _uiState.value = state
                }
            }

            is WeatherUiState.Error.UnknownHost -> {
                withContext(dispatchers.io()) {
                    val newState = dbRepository.getCacheWeather(cityName).map(mapper)
                    _uiState.value = newState
                }
            }

            else -> {
                _uiState.value = state
            }
        }
    }

    fun setImHere(value: String?) {
        viewModelScope.launch(dispatchers.io()) {
            _hereEventState.value = ImHereEvent.Loading
            try {
                val success = dbRepository.setImHere(value)
                if (success) {
                    _hereEventState.value = ImHereEvent.HereCity("")
                } else {
                    _hereEventState.value = ImHereEvent.NotHere
                }
            } catch (_: Exception) {
                _hereEventState.value = ImHereEvent.NotHere
            }
        }
    }

    fun getImHereCity() {
        viewModelScope.launch(dispatchers.io()) {
            _hereEventState.value = ImHereEvent.Loading
            try {
                val hereCity = dbRepository.getImHereCity()
                hereCity?.let { _hereEventState.value = ImHereEvent.HereCity(it) }
                    ?: { _hereEventState.value = ImHereEvent.NotHere }
            } catch (_: Exception) {
                _hereEventState.value = ImHereEvent.NotHere
            }
        }
    }
}

interface DispatchersList {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher

    class Base : DispatchersList {
        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun ui(): CoroutineDispatcher = Dispatchers.Main
    }
}