package com.dmgpersonal.androidonkotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class WeatherViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData()): ViewModel(){

    fun getLiveData() = liveData
    fun getWeather() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(2000)
            liveData.postValue(AppState.Success(AppState.Success(Any())))
        }.start()
    }
}