package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.*
import java.lang.Thread.sleep
import kotlin.random.Random

class WeatherViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryLocalSingle: RepositoryLocalSingle = RepositoryLocalSingleImpl(),
    private val repositoryLocalList: RepositoryLocalList = RepositoryLocalListImpl(),
    private val repositoryServerSingle: RepositoryServerSingle = RepositoryServerSingleImpl(),
    private val repositoryServerList: RepositoryServerList = RepositoryServerListImpl()
): ViewModel() {

    fun getLiveData() = liveData
    fun getWeatherFromLocalSourceSingle() = getDataFromLocalSourceSingle()
    fun getWeatherFromLocalSourceList() = getDataFromLocalSourceList()
    fun getWeatherFromServerSingle() = getDataFromServerSingle()
    fun getWeatherFromServerList() = getDataFromServerList()

    private fun getDataFromLocalSourceSingle() {
        val rnd = (0..10).random(Random(System.currentTimeMillis()))
        Thread {
            liveData.postValue(AppState.Loading)
            sleep(2000)
            when(rnd) {
                in 0..7 -> liveData.postValue(AppState.Success(repositoryLocalSingle.getWeatherFromLocalSource()))
                else -> liveData.postValue(AppState.Error(Throwable()))
            }
        }.start()
    }

    private fun getDataFromLocalSourceList(): Any {
        return Any()
    }

    private fun getDataFromLocalSource(): Any {
        return Any()
    }

    private fun getDataFromServerSingle(): Any {
        return Any()
    }

    private fun getDataFromServerList(): Any {
        return Any()
    }
}