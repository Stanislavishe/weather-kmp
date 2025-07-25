package com.multrm.testkmp.ui.homeScreen

sealed interface HomeUiState {
    data object Initial: HomeUiState
    data object Loading: HomeUiState
    data class Success(val data: List<String>): HomeUiState
    data class Error(val exception: Exception): HomeUiState
}