package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO

class RetrofitRepositoryImpl(private val remoteDataSource: RemoteDataSource): RetrofitDetailsRepository {
    override fun getWeather(lat: Double, lon: Double, callback: retrofit2.Callback<WeatherDTO>) {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }
}