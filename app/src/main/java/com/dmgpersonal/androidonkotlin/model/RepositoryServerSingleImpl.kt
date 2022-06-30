package com.dmgpersonal.androidonkotlin.model

class RepositoryServerSingleImpl : RepositoryServerSingle {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }
}