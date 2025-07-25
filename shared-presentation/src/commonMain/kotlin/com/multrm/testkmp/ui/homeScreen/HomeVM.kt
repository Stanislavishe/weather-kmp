package com.multrm.testkmp.ui.homeScreen

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multrm.shared.domain.WeatherDbRepository
import com.multrm.shared.domain.WeatherRepository
import com.multrm.testkmp.ui.events.ImHereEvent
import com.multrm.testkmp.ui.weatherScreen.DispatchersList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeVM (
    private val repository: WeatherRepository,
    private val dbRepository: WeatherDbRepository,
    private val dispatchers: DispatchersList
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _hereEventState = MutableStateFlow<ImHereEvent>(ImHereEvent.Initial)
    val hereEventState = _hereEventState.asStateFlow()

    sealed interface SearchState {
        data object Empty : SearchState
        data class UserQuery(val query: String) : SearchState
    }

    val textFieldState = TextFieldState()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val textState: StateFlow<SearchState> = snapshotFlow { textFieldState.text }
        .debounce(500)
        .mapLatest { if (it.isBlank()) SearchState.Empty else SearchState.UserQuery(it.toString()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = SearchState.Empty
        )

    fun getCitiesList() = viewModelScope.launch {
        _uiState.value = HomeUiState.Loading
        val citiesList = repository.getCitiesList()
        _uiState.value = HomeUiState.Success(citiesList)
        textState.onEach { state ->
            if (state is SearchState.Empty) {
                _uiState.value = HomeUiState.Success(citiesList)
            } else {
                val query = (state as SearchState.UserQuery).query.lowercase()
                val filterList = citiesList.filter { city ->
                    query.all {
                        city.lowercase().toCharArray().contains(it)
                    }
                }
                _uiState.value = HomeUiState.Success(filterList)
            }
        }.launchIn(this)
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