package com.dmgpersonal.androidonkotlin.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.BuildConfig
import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO
import com.dmgpersonal.androidonkotlin.utils.YANDEX_API_KEY
import com.dmgpersonal.androidonkotlin.utils.getLines
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherDTOModel(
    private val liveDataDTO: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    fun getLiveDataDTO() = liveDataDTO

    fun getWeather(city: City) = getWeatherFromServer(city)


    private fun getWeatherFromServer(city: City) {
        liveDataDTO.value = AppState.Loading
        requestToServer(city)
    }

    private fun requestToServer(city: City) {
        var uri: URL? = null
        try {
            uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        } catch (e: MalformedURLException) {
            liveDataDTO.value = AppState.Error(e)
            Log.e("@@@", "Fail URI", e)
            e.printStackTrace()
        }

        var connection: HttpsURLConnection? = null

        try {

            connection = uri?.openConnection() as HttpsURLConnection
            connection.also {
                it.readTimeout = 5000
                it.addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            }

            Thread {
                val reader: BufferedReader
                try {
                    reader = BufferedReader(InputStreamReader(connection.inputStream))
                } catch (e: RuntimeException) {
                    liveDataDTO.postValue(AppState.Error(e))
                    Log.e("@@@", "Cannot receive data from server", e)
                    e.printStackTrace()
                    return@Thread
                }
                val weather: WeatherDTO
                try {
                    weather = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                } catch (e: JsonSyntaxException) {
                    liveDataDTO.postValue(AppState.Error(e))
                    Log.e("@@@", "Cannot receive data from server", e)
                    e.printStackTrace()
                    return@Thread
                }
                Handler(Looper.getMainLooper()).post {
                    liveDataDTO.postValue(AppState.SuccessFromServer(weather))
                }
            }.start()
        } catch (e: RuntimeException) {
            liveDataDTO.postValue(AppState.Error(e))
            Log.e("@@@", "Cannot receive data from server", e)
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }

    }
}