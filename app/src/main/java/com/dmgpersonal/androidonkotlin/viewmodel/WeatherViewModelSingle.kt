package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.*
import java.lang.Thread.sleep
import kotlin.random.Random

class WeatherViewModelSingle(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(),
): ViewModel() {

    fun getLiveData() = liveData
    fun getWeatherFromLocalSourceSingle() = getDataFromLocalSourceSingle()

    private fun getDataFromLocalSourceSingle() {
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(2000)
            when((0..10).random(Random(System.currentTimeMillis()))) {
                in 0..7 -> liveData.postValue(AppState.SuccessSingle(repository.getWeatherFromServer()))
                else -> liveData.postValue(AppState.Error(Throwable()))
            }
        }.start()
    }
}