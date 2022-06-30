package com.dmgpersonal.androidonkotlin.view.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.databinding.FragmentCitiesListBinding
import com.dmgpersonal.androidonkotlin.model.Location.*
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.model.getDefaultCity
import com.dmgpersonal.androidonkotlin.view.details.WeatherFragmentDetail
import com.dmgpersonal.androidonkotlin.viewmodel.AppState
import com.dmgpersonal.androidonkotlin.viewmodel.WeatherViewModelList
import com.google.android.material.snackbar.Snackbar

class CitiesListFragment: Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WeatherViewModelList
    private val adapter = CitiesFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if(manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(WeatherFragmentDetail.BUNDLE_EXTRA, weather)
                manager.beginTransaction()
                    .add(R.id.container, WeatherFragmentDetail.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })
    private var location = Russia

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeDataSet() }
        viewModel = ViewModelProvider(this)[WeatherViewModelList::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun renderData(appState: AppState?) {
        when(appState) {
            is AppState.SuccessList -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }

            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainFragmentFAB,
                getString(R.string.error),
                Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.reload)) {
                        when (location) {
                            Russia -> viewModel.getWeatherFromLocalSourceRus()
                            World -> viewModel.getWeatherFromLocalSourceWorld()
                        }
                    }.show()
            }

            else -> {}
        }
    }

    private fun changeDataSet() {
        when(location) {
            Russia -> {
                viewModel.getWeatherFromLocalSourceWorld()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
            }
            World -> {
                viewModel.getWeatherFromLocalSourceRus()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            }
        }

        location = !location
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}