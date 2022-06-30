package com.dmgpersonal.androidonkotlin.model

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalSourceRus(): List<Weather>
    fun getWeatherFromLocalSourceWorld(): List<Weather>
}

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalSourceRus(): List<Weather> {
        return getWeatherFromLocalSourceRus()
    }

    override fun getWeatherFromLocalSourceWorld(): List<Weather> {
        return getWeatherFromLocalSourceWorld()
    }

}