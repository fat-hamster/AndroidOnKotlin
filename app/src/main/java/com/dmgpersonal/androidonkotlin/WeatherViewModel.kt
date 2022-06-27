package com.dmgpersonal.androidonkotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class WeatherViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
): ViewModel() {

    fun getLiveData() = liveData
    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteServer() = getDataFromLocalSource() // TODO: исправить!

    private fun getDataFromLocalSource() {
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(2000)
            liveData.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalSource()))
        }.start()
    }
}