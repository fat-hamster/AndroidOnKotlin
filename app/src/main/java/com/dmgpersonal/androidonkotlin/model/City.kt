package com.dmgpersonal.androidonkotlin.model

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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

fun getAddress(lat: Double, lng: Double): String {
    val geocoder = Geocoder(MyApp.appContext)
    var location: String
    try {
        val list = geocoder.getFromLocation(lat, lng, 1)
        location = list[0].locality
    } catch (e: IOException) {
        location = "Unknown"
        Log.d("@@@", e.toString())
    }

    return location
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
