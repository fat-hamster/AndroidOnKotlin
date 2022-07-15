package com.dmgpersonal.androidonkotlin.repository

import com.dmgpersonal.androidonkotlin.BuildConfig
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO
import com.dmgpersonal.androidonkotlin.utils.YANDEX_BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val weatherApi = Retrofit.Builder()
        .baseUrl(YANDEX_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(WeatherAPI::class.java)
    fun getWeatherDetails(lat: Double, lon: Double, callback:
    Callback<WeatherDTO>
    ) {
        weatherApi.getWeather(
            BuildConfig.WEATHER_API_KEY, lat,
            lon).enqueue(callback)
    }
}