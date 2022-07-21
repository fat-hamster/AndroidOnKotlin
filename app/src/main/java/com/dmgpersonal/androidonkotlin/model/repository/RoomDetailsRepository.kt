package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.model.Weather

interface RoomDetailsRepository {
    fun getWeather(
        lat: Double,
        lon: Double,
        callback: ResponseCallback
    )
}

interface ResponseCallback {
    fun onResponse(weather: Weather)
    fun onFailure(e: Exception)
}