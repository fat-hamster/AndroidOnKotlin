package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.MyApp
import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.room.WeatherDatabase
import com.dmgpersonal.androidonkotlin.model.room.WeatherEntity

class RoomRepositoryImpl: RoomDetailsRepository, RoomInsertWeather {
    override fun getWeather(lat: Double, lon: Double, callback: ResponseCallback) {
        callback.onResponse(WeatherDatabase.invoke(MyApp.appContext)
            .weatherDao()
            .getWeatherByCoordinates(lat, lon).let {
            convertEntityToWeather(it).last()
        })
    }

    override fun saveWeather(weather: Weather) {
        WeatherDatabase.invoke(MyApp.appContext).weatherDao().insert(convertWeatherToEntity(weather))
    }

    private fun convertWeatherToEntity(weather: Weather): WeatherEntity {
        return weather.let {
            WeatherEntity(0, it.city.name, it.city.lat, it.city.lon,
                it.temperature, it.feelsLike, it.icon)
        }
    }

    private fun convertEntityToWeather(entity: List<WeatherEntity>): List<Weather> {
        return entity.map {
            Weather(City(it.name, it.lat, it.lon), it.temperature, it.feelsLike, it.icon)
        }
    }
}