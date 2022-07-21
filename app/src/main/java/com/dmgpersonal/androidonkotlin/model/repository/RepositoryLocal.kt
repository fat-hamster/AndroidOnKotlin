package com.dmgpersonal.androidonkotlin.model.repository

import com.dmgpersonal.androidonkotlin.model.Location
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.getRussianCities
import com.dmgpersonal.androidonkotlin.model.getWorldCities

fun interface RepositoryWeatherFromLocal {
    fun getWeather(hasInternet: Boolean, location: Location): List<Weather>
}


class RepositoryLocalImpl : RepositoryWeatherFromLocal {
    override fun getWeather(
        hasInternet: Boolean,
        location: Location
    ): List<Weather> = when (hasInternet) {
            true -> getWeatherFromServer()
            else -> when (location) {
                Location.World -> getWeatherFromLocalSourceWorld()
                Location.Russia -> getWeatherFromLocalSourceRus()
            }}

    private fun getWeatherFromServer(): List<Weather> = listOf(Weather())

    private fun getWeatherFromLocalSourceRus(): List<Weather> = getRussianCities()

    private fun getWeatherFromLocalSourceWorld(): List<Weather> = getWorldCities()
}