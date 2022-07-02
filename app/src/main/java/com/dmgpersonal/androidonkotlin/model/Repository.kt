package com.dmgpersonal.androidonkotlin.model

fun interface Repository {

    fun getWeather(hasInternet: Boolean, location: Location): List<Weather>
}

class RepositoryImpl : Repository {

    // параметр hasInternet временный, потом проверятся будет тут и передавать его будет ненужно.
    override fun getWeather(hasInternet: Boolean, location: Location): List<Weather> {
        return if (hasInternet)
            getWeatherFromServer(location)
        else if (location == Location.World)
            getWeatherFromLocalSourceWorld()
        else
            getWeatherFromLocalSourceRus()
    }

    // Ответ от сервера в любом случе будет списком
    private fun getWeatherFromServer(location: Location): List<Weather> {
        return listOf(Weather())
    }

    private fun getWeatherFromLocalSourceRus(): List<Weather> {
        return getRussianCities()
    }

    private fun getWeatherFromLocalSourceWorld(): List<Weather> {
        return getWorldCities()
    }
}