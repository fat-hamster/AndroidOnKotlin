package com.dmgpersonal.androidonkotlin.repository

import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO

interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}