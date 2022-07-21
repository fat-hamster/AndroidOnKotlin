package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.MyApp
import com.dmgpersonal.androidonkotlin.model.dto.FactDTO
import com.dmgpersonal.androidonkotlin.model.dto.InfoDTO
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO
import com.dmgpersonal.androidonkotlin.model.room.WeatherDatabase
import com.dmgpersonal.androidonkotlin.model.room.WeatherEntity

class RoomRepositoryImpl: RoomDetailsRepository {
    override fun getWeather(lat: Double, lon: Double, callback: ResponseCallback) {
        callback.onResponse(WeatherDatabase.invoke(MyApp.appContext)
            .weatherDao()
            .getWeatherByCoordinates(lat, lon).let {
            convertEntityToWeather(it).last()
        })
    }

//    fun convertWeatherToModel(weatherDTO: WeatherDTO): WeatherEntity {
//
//    }

    private fun convertEntityToWeather(entity: List<WeatherEntity>): List<WeatherDTO> {
        return entity.map {
            WeatherDTO(FactDTO(it.feelsLike, it.icon, it.temperature), InfoDTO(it.lat, it.lon)
            )
        }
    }
}