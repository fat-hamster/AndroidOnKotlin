package com.dmgpersonal.androidonkotlin.model

class RepositoryLocalSingleImpl : RepositoryLocalSingle {
    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }
}