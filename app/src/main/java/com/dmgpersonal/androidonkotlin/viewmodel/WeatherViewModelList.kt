package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.*
import java.lang.Thread.sleep
import kotlin.random.Random

class WeatherViewModelList(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
): ViewModel() {

    fun getLiveData() = liveData
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSourceList(Location.Russia)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSourceList(Location.World)

    private fun getDataFromLocalSourceList(location: Location) {
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(2000)
            when((0..10).random(Random(System.currentTimeMillis()))) {
                in 0..7 -> liveData.postValue(
                    AppState.SuccessList(repository.getWeather(false, location)))
                else -> liveData.postValue(AppState.Error(Throwable()))
            }
        }.start()
    }
}