package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.Location
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.repository.RepositoryLocalImpl
import com.dmgpersonal.androidonkotlin.model.repository.RepositoryWeatherFromLocal

class WeatherViewModelList(
    private val liveData: MutableLiveData<AppStateLocal> = MutableLiveData(),
    private val repository: RepositoryWeatherFromLocal = RepositoryLocalImpl()
): ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(location: Location) = getDataFromLocalSourceList(location)

    private fun getDataFromLocalSourceList(location: Location) {
        liveData.value = AppStateLocal.Success(repository.getWeather(false, location))
    }
}