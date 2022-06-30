package com.dmgpersonal.androidonkotlin.model

fun interface RepositoryServerList {
    fun getWeatherFromServer() : List<Weather>
}