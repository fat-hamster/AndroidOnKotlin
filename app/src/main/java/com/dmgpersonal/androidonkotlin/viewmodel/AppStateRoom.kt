package com.dmgpersonal.androidonkotlin.viewmodel

import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO

sealed class AppStateRoom {
    data class Success(val weather: List<Weather>) : AppStateRoom()
    data class Error(val error: Throwable) : AppStateRoom()
    object Loading : AppStateRoom()
}
