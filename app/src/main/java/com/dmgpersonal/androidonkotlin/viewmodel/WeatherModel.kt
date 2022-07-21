package com.dmgpersonal.androidonkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.dto.WeatherDTO
import com.dmgpersonal.androidonkotlin.model.getAddress
import com.dmgpersonal.androidonkotlin.model.repository.RemoteDataSource
import com.dmgpersonal.androidonkotlin.model.repository.RetrofitRepositoryImpl
import com.dmgpersonal.androidonkotlin.utils.REQUEST_ERROR
import com.dmgpersonal.androidonkotlin.utils.SERVER_ERROR

class WeatherModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    fun getLiveData() = liveData

    fun getWeather(city: City) = getWeatherFromServer(city)


    private fun getWeatherFromServer(city: City) {
        liveData.value = AppState.Loading
        val detailsRepositoryImpl = RetrofitRepositoryImpl(RemoteDataSource())
        detailsRepositoryImpl.getWeather(city.lat, city.lon, callBack)
    }

    private val callBack = object : retrofit2.Callback<WeatherDTO> {
        override fun onResponse(
            call: retrofit2.Call<WeatherDTO>,
            response: retrofit2.Response<WeatherDTO>
        ) {
            val serverResponse: WeatherDTO? = response.body()

            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    AppState.Success(convertDtoToWeather(serverResponse))
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun convertDtoToWeather(weatherDTO: WeatherDTO): Weather {
        return Weather(
            City(getAddress(weatherDTO.info.lat, weatherDTO.info.lon),
                weatherDTO.info.lat, weatherDTO.info.lon),
            temperature = weatherDTO.fact.temp,
            feelsLike = weatherDTO.fact.feelsLike,
            icon = weatherDTO.fact.icon
        )
    }
}