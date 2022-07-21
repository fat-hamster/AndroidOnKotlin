package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.MyApp
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.room.WeatherDatabase
import com.dmgpersonal.androidonkotlin.utils.convertEntityToWeather
import com.dmgpersonal.androidonkotlin.utils.convertWeatherToEntity

class RoomRepositoryImpl: RoomDetailsRepository, RoomInsertWeather, AllWeatherFromRoom {
    override fun getWeather(lat: Double, lon: Double, callback: ResponseCallback) {
        callback.onResponse(WeatherDatabase.invoke(MyApp.appContext)
            .weatherDao()
            .getWeatherByCoordinates(lat, lon).let {
            convertEntityToWeather(it).last()
        })
    }

    override fun saveWeather(weather: Weather) {
        Thread {
            WeatherDatabase.invoke(MyApp.appContext).weatherDao()
                .insert(convertWeatherToEntity(weather))
        }.start()
    }

    override fun getAllWeatherFromHistory(): List<Weather> {
        return convertEntityToWeather(WeatherDatabase.invoke(MyApp.appContext).weatherDao().getAllWeather())
    }
}