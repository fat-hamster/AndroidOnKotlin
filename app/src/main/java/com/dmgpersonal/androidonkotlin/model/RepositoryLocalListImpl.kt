package com.dmgpersonal.androidonkotlin.model

class RepositoryLocalListImpl : RepositoryLocalList {
    override fun getWeatherFromLocalSource(): List<Weather> {
        return listOf(Weather())
    }
}