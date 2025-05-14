package com.jihan.composeutils.core

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data object Idle : UiState<Nothing>()
}



