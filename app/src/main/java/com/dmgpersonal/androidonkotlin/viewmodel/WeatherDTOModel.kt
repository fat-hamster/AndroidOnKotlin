package com.dmgpersonal.androidonkotlin.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.BuildConfig
import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.RemoteRepository
import com.dmgpersonal.androidonkotlin.model.RemoteRepositoryImpl
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO
import com.dmgpersonal.androidonkotlin.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class WeatherDTOModel (
    private val liveDataDTO: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RemoteRepository =  RemoteRepositoryImpl()
) : ViewModel() {

    fun getLiveDataDTO() = liveDataDTO

    fun getWeather(city: City) = getWeatherFromServer(city)


    private fun getWeatherFromServer(city: City) {
        liveDataDTO.value = AppState.Loading
        requestToServer(city)

    }

    private fun requestToServer(city: City) {
        val uri =
            URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        val connection: HttpsURLConnection?

        try {
            connection = uri.openConnection() as HttpsURLConnection
            connection.readTimeout = 10000
            connection.addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)

            Thread {
                try {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val weather = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                    Handler(Looper.getMainLooper()).post {
                        liveDataDTO.postValue(AppState.SuccessFromServer(weather))
                    }
                } catch (e : Exception) {
                    liveDataDTO.postValue(AppState.Error(e))
                    Log.e("@@@", "Cannot receive data from server", e)
                    e.printStackTrace()
                } finally {
                    connection.disconnect()
                }
            }.start()
        } catch (e : MalformedURLException) {
            liveDataDTO.value = AppState.Error(e)
            Log.e("@@@", "Fail URI", e)
            e.printStackTrace()
        }
    }
}