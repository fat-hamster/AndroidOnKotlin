package com.dmgpersonal.androidonkotlin.view.details

import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.databinding.FragmentWeatherDetailBinding
import com.dmgpersonal.androidonkotlin.model.City
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.utils.YANDEX_WEATHER_ICON
import com.dmgpersonal.androidonkotlin.viewmodel.AppState
import com.dmgpersonal.androidonkotlin.viewmodel.WeatherDTOModel
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class WeatherFragmentDetails : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): WeatherFragmentDetails {
            return WeatherFragmentDetails().apply { arguments = bundle }
        }
    }

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!
    private var city: City = Weather().city // default city

    private val viewModel: WeatherDTOModel by lazy {
        ViewModelProvider(this)[WeatherDTOModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { weather ->
            city = weather.city
        }

        viewModel.getLiveDataDTO().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        viewModel.getWeather(city)
    }

    //@Suppress("IMPLICIT_CAST_TO_ANY")
    private fun renderData(appState: AppState) = when (appState) {
        is AppState.SuccessFromServer -> {
            with(binding) {
                Thread {
                    appState.weatherDTO.info.apply {
                        Handler(Looper.getMainLooper()).post { cityName.text = getAddress(lat, lon) }
                    }
                }.start()

                appState.weatherDTO.fact.let {
                    temperatureValue.text = it.temp.toString()
                    feelsLikeValue.text = it.feelsLike.toString()
                    weatherIcon.loadSVG("$YANDEX_WEATHER_ICON${it.icon}.svg")
                }
            }
        }
        is AppState.Error -> {
            Snackbar
                .make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload") { viewModel.getWeather(city) }
                .show()
        }
        else -> {}
    }.also {
        if (appState == AppState.Loading) binding.loadingLayout.visibility = View.VISIBLE
        else binding.loadingLayout.visibility = View.GONE
    }

    private fun AppCompatImageView.loadSVG(url: String) {
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val request = ImageRequest.Builder(this.context)
            .placeholder(R.drawable.loading)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, resources.configuration.locales.get(0))
        var currentLocation: String
        try {
            val list = geocoder.getFromLocation(lat, lng, 1)
            currentLocation = list[0].locality//getAddressLine(0)
        } catch (e: IOException) {
            currentLocation = "Unknown"
            Log.d("@@@", e.toString())
        }

        return currentLocation
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}