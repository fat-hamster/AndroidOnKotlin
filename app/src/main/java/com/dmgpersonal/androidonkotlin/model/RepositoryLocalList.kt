package com.dmgpersonal.androidonkotlin.model

fun interface RepositoryLocalList {
    fun getWeatherFromLocalSource() : List<Weather>
}