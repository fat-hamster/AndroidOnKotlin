package com.dmgpersonal.androidonkotlin.model

fun interface RepositoryLocalSingle {
    fun getWeatherFromLocalSource() : Weather
}