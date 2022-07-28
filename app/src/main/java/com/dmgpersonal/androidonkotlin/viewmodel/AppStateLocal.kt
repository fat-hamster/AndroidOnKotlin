package com.dmgpersonal.androidonkotlin.viewmodel

import com.dmgpersonal.androidonkotlin.model.Weather

sealed class AppStateLocal {
    data class Success(val weatherData: List<Weather>) : AppStateLocal()
    data class Error(val error: Throwable) : AppStateLocal()
    object Loading : AppStateLocal()
}
