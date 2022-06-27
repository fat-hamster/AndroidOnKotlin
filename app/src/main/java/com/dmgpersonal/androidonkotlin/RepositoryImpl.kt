package com.dmgpersonal.androidonkotlin

class RepositoryImpl : Repository {
    override fun getWeatherFromLocalSource(): Weather {
        return Weather()
    }

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }
}