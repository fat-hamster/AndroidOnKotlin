package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO

interface RetrofitDetailsRepository {
    fun getWeather(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}