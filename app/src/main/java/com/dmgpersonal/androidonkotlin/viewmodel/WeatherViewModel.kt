package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.Repository
import com.dmgpersonal.androidonkotlin.model.RepositoryImpl
import java.lang.Thread.sleep

class WeatherViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
): ViewModel() {

    fun getLiveData() = liveData
    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteServer() = getDataFromLocalSource() // TODO: исправить!

    private fun getDataFromLocalSource() {
        val rnd = (0..10).random()
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(2000)
            when(rnd) {
                in 0..7 -> liveData.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalSource()))
                else -> liveData.postValue(AppState.Error(Throwable()))
            }
        }.start()
    }
}