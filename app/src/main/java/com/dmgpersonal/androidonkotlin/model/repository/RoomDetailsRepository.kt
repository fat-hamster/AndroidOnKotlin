package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO

interface RoomDetailsRepository {
    fun getWeather(
        lat: Double,
        lon: Double,
    callback: ResponseCallback
    )
}

interface ResponseCallback {
    fun onResponse(weatherDTO: WeatherDTO)
    fun onFailure(e: Exception)
}