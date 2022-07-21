package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.model.Weather

fun interface RoomInsertWeather {
    fun saveWeather(weather: Weather)
}