package com.dmgpersonal.androidonkotlin.viewmodel

import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class SuccessFromServer(val weatherDTO: WeatherDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
