package com.dmgpersonal.androidonkotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.databinding.FragmentWeatherDetailBinding
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.viewmodel.AppState
import com.dmgpersonal.androidonkotlin.viewmodel.WeatherViewModelSingle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_detail.*

class WeatherFragmentDetail : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): WeatherFragmentDetail {
            return WeatherFragmentDetail().apply { arguments = bundle }
        }
    }

//    private lateinit var viewModel: WeatherViewModelSingle
    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
        if (weather != null) {
            val city = weather.city
            binding.cityName.text = city.name
            binding.cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat,
                city.lon
            )
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()
        }
    }

//    private fun renderData(appState: AppState) {
//        when (appState) {
//            is AppState.SuccessSingle -> {
//                val weatherData = appState.weatherData
//                binding.loadingLayout.visibility = View.GONE
//                setData(weatherData)
//            }
//
//            is AppState.SuccessList -> {
//                // TODO: что-то придумать))))
//            }
//
//            is AppState.Loading -> {
//                binding.loadingLayout.visibility = View.VISIBLE
//            }
//
//            is AppState.Error -> {
//                binding.loadingLayout.visibility = View.GONE
//                Snackbar.make(requireView(), "Error", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Reload") { viewModel.getWeatherFromLocalSourceSingle() }
//                    .show()
//            }
//        }
//    }
//
//    private fun setData(weatherData: Weather) {
//        binding.apply {
//            cityName.text = weatherData.city.name
//            cityCoordinates.text = String.format(
//                getString(R.string.city_coordinates),
//                weatherData.city.lat.toString(),
//                weatherData.city.lon.toString()
//            )
//            temperatureValue.text = weatherData.temperature.toString()
//            feelsLikeValue.text = weatherData.feelsLike.toString()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}