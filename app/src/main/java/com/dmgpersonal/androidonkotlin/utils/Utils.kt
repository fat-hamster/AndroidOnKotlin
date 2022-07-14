package com.dmgpersonal.androidonkotlin.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import java.io.BufferedReader
import java.util.stream.Collectors

const val YANDEX_API_KEY = "X-Yandex-API-Key"
const val YANDEX_LINK = "https://api.weather.yandex.ru/v2/informers?"

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}

// Information:
// Все что ниже - честно украдено из интернета.

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun onAlertDialog(view: View, message: String) {
    val builder = AlertDialog.Builder(view.context)

    builder.setTitle("Status")

    builder.setMessage(message)

    // эти методы не удаляю, могут пригодиться

    /*builder.setPositiveButton("Update Now") { _, _ ->
        // User clicked Update Now button
        Toast.makeText(view.context, "Updating your device",Toast.LENGTH_SHORT).show()
    }

    //set positive button
    builder.setNegativeButton(
        "Cancel") { dialog, id ->
        // User cancelled the dialog
    }*/

    //set neutral button
    builder.setNeutralButton("Ok") { _, _ ->
        return@setNeutralButton
    }
    builder.show()
}

class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        updateConnection()
        connectivityManager.registerDefaultNetworkCallback(getConnectivityMarshmallowManagerCallback())
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    private fun getConnectivityMarshmallowManagerCallback(): ConnectivityManager.NetworkCallback {

        object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                networkCapabilities?.let { capabilities ->
                    if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && capabilities.hasCapability(
                            NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
                        postValue(true)
                    }
                }
            }

            override fun onLost(network: Network) {
                postValue(false)
            }
        }.also { connectivityManagerCallback = it }
        return connectivityManagerCallback
    }

    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }
}