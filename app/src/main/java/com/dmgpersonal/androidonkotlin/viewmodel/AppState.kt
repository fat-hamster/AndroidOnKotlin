package com.dmgpersonal.androidonkotlin.viewmodel

import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

sealed class Location {
    data class Russia(
        val Moscow: Weather = Weather(City("Москва", 55.755826, 37.617299900000035))
    ) : Location()
    //data class World() : Location()
}