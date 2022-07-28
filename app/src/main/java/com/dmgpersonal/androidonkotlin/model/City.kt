package com.dmgpersonal.androidonkotlin.model

import android.location.Geocoder
import android.os.Parcelable
import android.util.Log
import com.dmgpersonal.androidonkotlin.MyApp
import kotlinx.android.parcel.Parcelize
import java.io.IOException

@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
) : Parcelable

fun getAddress(lat: Double, lon: Double): City {
    val geocoder = Geocoder(MyApp.appContext)
    var currentCityName: City
    try {
        val list = geocoder.getFromLocation(lat, lon, 1).last()
        if(list.locality != null) {
            currentCityName = City(list.locality, lat, lon)
        } else {
            currentCityName = City(list.getAddressLine(0), lat, lon)
        }
    } catch (e: IOException) {
        currentCityName = getDefaultCity()
        Log.d("@@@", e.toString())
    }

    return currentCityName
}

fun getCoordinates(cityName: String): City {
    val geocoder = Geocoder(MyApp.appContext)
    var currentLocation: City
    try {
        val location = geocoder.getFromLocationName(cityName, 1)
        currentLocation = City(cityName, location.first().latitude, location.first().longitude)
    } catch (e: IOException) {
        currentLocation = getDefaultCity()
        Log.d("@@@", e.toString())
    }
    return currentLocation
}