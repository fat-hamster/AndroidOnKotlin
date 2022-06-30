package com.dmgpersonal.androidonkotlin.model

class RepositoryServerListImpl : RepositoryServerList {

    override fun getWeatherFromServer(): List<Weather> {
        return listOf(Weather())
    }
}