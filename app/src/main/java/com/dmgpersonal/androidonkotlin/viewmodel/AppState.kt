package com.dmgpersonal.androidonkotlin.viewmodel

import com.dmgpersonal.androidonkotlin.model.Weather

sealed class AppState {
    data class SuccessSingle(val weatherData: Weather) : AppState()
    data class SuccessList(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
