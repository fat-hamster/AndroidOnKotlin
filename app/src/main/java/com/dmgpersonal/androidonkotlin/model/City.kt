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

// От данной функции пока мало проку, но ближе к концу курса она обязательно понадобится! :)
fun getAddress(lat: Double, lng: Double): String {
    val geocoder = Geocoder(MyApp.appContext, MyApp.appContext.resources.configuration.locales.get(0))
    var currentLocation: String
    try {
        val list = geocoder.getFromLocation(lat, lng, 1)
        currentLocation = list[0].locality
    } catch (e: IOException) {
        currentLocation = "Unknown"
        Log.d("@@@", e.toString())
    }

    return currentLocation
}
