package com.dmgpersonal.androidonkotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class WeatherViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData()): ViewModel(){
    fun getData(): LiveData<Any> {
        getDataFromLocalSource()
        return liveData
    }

    private fun getDataFromLocalSource() {
        Thread {
            sleep(2000)
            liveData.postValue(Any())
        }.start()
    }
}