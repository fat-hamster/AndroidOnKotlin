package com.dmgpersonal.androidonkotlin.viewmodel

import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO

sealed class AppState {
    data class Success(val weatherDTO: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
