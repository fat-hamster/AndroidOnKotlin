package com.dmgpersonal.androidonkotlin.utils

import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO
import com.dmgpersonal.androidonkotlin.model.getAddress
import com.dmgpersonal.androidonkotlin.model.room.WeatherEntity

fun convertDtoToWeather(weatherDTO: WeatherDTO): Weather {
    return Weather(getAddress(weatherDTO.info.lat, weatherDTO.info.lon),
        temperature = weatherDTO.fact.temp,
        feelsLike = weatherDTO.fact.feelsLike,
        icon = weatherDTO.fact.icon
    )
}

fun convertWeatherToEntity(weather: Weather): WeatherEntity {
    return weather.let {
        WeatherEntity(0, it.city.name, it.city.lat, it.city.lon,
            it.temperature, it.feelsLike, it.icon)
    }
}

fun convertEntityToWeather(entity: List<WeatherEntity>): List<Weather> {
    return entity.map {
        Weather(City(it.name, it.lat, it.lon), it.temperature, it.feelsLike, it.icon)
    }
}