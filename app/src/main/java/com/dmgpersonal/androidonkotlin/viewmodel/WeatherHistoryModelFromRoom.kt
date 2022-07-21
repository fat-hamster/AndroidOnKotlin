package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.repository.ResponseCallback
import com.dmgpersonal.androidonkotlin.model.repository.RoomRepositoryImpl

class WeatherHistoryModelFromRoom(
    private val liveData: MutableLiveData<AppStateRoom> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getAllWeather() = getAllWeatherFromRoom()

    private fun getAllWeatherFromRoom() {
        liveData.value = AppStateRoom.Loading
        val weatherModelFromRoom = RoomRepositoryImpl()
        Thread {
            liveData.postValue(AppStateRoom.Success(weatherModelFromRoom.getAllWeatherFromHistory()))
        }.start()
    }

}