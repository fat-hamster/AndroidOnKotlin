package com.dmgpersonal.androidonkotlin.view.cities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.databinding.FragmentCitiesListBinding
import com.dmgpersonal.androidonkotlin.model.Location.Russia
import com.dmgpersonal.androidonkotlin.model.Location.World
import com.dmgpersonal.androidonkotlin.model.Weather
import com.dmgpersonal.androidonkotlin.utils.SP_KEY_LOCATION
import com.dmgpersonal.androidonkotlin.utils.SP_REGION_SETTINGS
import com.dmgpersonal.androidonkotlin.view.details.WeatherFragmentDetails
import com.dmgpersonal.androidonkotlin.view.map.MapsFragment
import com.dmgpersonal.androidonkotlin.viewmodel.AppStateLocal
import com.dmgpersonal.androidonkotlin.viewmodel.WeatherViewModelList
import com.google.android.material.snackbar.Snackbar

class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null
    private var location = Russia // Временное решение, потом локацию будем получать от Android

    private val viewModel: WeatherViewModelList by lazy {
        ViewModelProvider(this)[WeatherViewModelList::class.java]
    }

    private val adapter = CitiesFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.run {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, WeatherFragmentDetails.newInstance(
                        Bundle().apply {
                            putParcelable(WeatherFragmentDetails.BUNDLE_EXTRA, weather)
                        }
                    ))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = context?.getSharedPreferences(SP_REGION_SETTINGS, Context.MODE_PRIVATE)
        location = when(sharedPreferences?.getBoolean(SP_KEY_LOCATION, true)) {
            true -> Russia
            false -> World
            else -> Russia
        }

        _binding = FragmentCitiesListBinding.inflate(inflater, container, false)

        when (location) {
            Russia -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            World -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeDataSet() }

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        viewModel.getWeather(location)

        binding.mapFragmentFAB.setOnClickListener {
            activity?.run {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, MapsFragment.newInstance())
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }

    private fun renderData(appState: AppStateLocal) = when (appState) {
        is AppStateLocal.Success -> {
            adapter.setWeather(appState.weatherData)
        }
        is AppStateLocal.Error -> {
            with(binding) {
                citiesFragmentRootLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeather(location) })
            }
        }
        else -> {}
    }.also {
        if (appState == AppStateLocal.Loading) binding.citiesFragmentLoadingLayout.visibility =
            View.VISIBLE
        else binding.citiesFragmentLoadingLayout.visibility = View.GONE
    }

    private fun changeDataSet() {
        location = !location
        viewModel.getWeather(location)
        when (location) {
            Russia -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            World -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
        }

        sharedPreferences?.apply {
            edit()
                .putBoolean(SP_KEY_LOCATION, when(location) {
                    Russia -> true
                    World -> false })
                .apply()
        }
    }


    override fun onDestroy() {
        _binding = null
        adapter.removeListener()
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}