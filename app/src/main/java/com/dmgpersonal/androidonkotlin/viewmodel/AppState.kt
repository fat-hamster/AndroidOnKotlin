package com.dmgpersonal.androidonkotlin.viewmodel

import com.dmgpersonal.androidonkotlin.model.Weather

sealed class AppState {
    data class Success(val weather: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
