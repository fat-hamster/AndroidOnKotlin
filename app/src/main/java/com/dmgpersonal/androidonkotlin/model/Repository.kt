package com.dmgpersonal.androidonkotlin.model

interface Repository {
    fun getWeatherFromLocalSource() : Weather
    fun getWeatherFromServer() : Weather
}