package com.dmgpersonal.androidonkotlin.model

class RepositoryImpl : Repository {
    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }
}