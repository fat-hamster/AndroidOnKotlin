package com.dmgpersonal.androidonkotlin

interface Repository {
    fun getWeatherFromLocalSource() : Weather
    fun getWeatherFromServer() : Weather
}