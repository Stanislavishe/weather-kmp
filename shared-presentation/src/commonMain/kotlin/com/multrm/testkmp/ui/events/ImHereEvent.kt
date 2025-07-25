package com.multrm.testkmp.ui.events

sealed interface ImHereEvent {
    data object Initial: ImHereEvent
    data object Loading: ImHereEvent
    data class HereCity(val city: String): ImHereEvent
    data object NotHere: ImHereEvent
}