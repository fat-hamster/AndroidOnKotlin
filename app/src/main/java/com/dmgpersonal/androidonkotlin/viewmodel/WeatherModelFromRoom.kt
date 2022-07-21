package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.repository.ResponseCallback
import com.dmgpersonal.androidonkotlin.model.repository.RoomRepositoryImpl

class WeatherModelFromRoom(
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City) = getWeatherFromRoom(city)

    fun saveWeather(weather: Weather) = RoomRepositoryImpl().saveWeather(weather)


    private fun getWeatherFromRoom(city: City) {
        liveData.value = AppState.Loading
        val detailsRepositoryImpl = RoomRepositoryImpl()
        Thread {
            detailsRepositoryImpl.getWeather(city.lat, city.lon, callBack)
        }.start()
    }

    private val callBack = object : ResponseCallback {
        override fun onResponse(weather: Weather) {
            liveData.postValue(AppState.Success(weather))
        }

        override fun onFailure(e: Exception) {
            liveData.postValue(AppState.Error(e))
        }
    }

}