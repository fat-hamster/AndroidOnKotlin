package com.dmgpersonal.androidonkotlin.view.cities

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
import com.dmgpersonal.androidonkotlin.view.details.WeatherFragmentDetail
import com.dmgpersonal.androidonkotlin.viewmodel.AppState
import com.dmgpersonal.androidonkotlin.viewmodel.WeatherViewModelList
import com.google.android.material.snackbar.Snackbar

class CitiesListFragment : Fragment() {

    private var _binding: FragmentCitiesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModelList by lazy {
        ViewModelProvider(this)[WeatherViewModelList::class.java]
    }

    private val adapter = CitiesFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            activity?.run { // run - название подходит больше по смыслу, потому run, а не apply
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, WeatherFragmentDetail.newInstance(
                        // а здесь как раз apply, по тем же причинам, что и run
                        Bundle().apply {
                            putParcelable(WeatherFragmentDetail.BUNDLE_EXTRA, weather)}
                    ))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private var location = Russia // Временное решение, потом локацию будем получать от Android

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) { Snackbar.make(this, text, length).setAction(actionText, action).show() }

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

        viewModel.getLiveData().observe(viewLifecycleOwner) { appState -> renderData(appState) }
        viewModel.getWeather(location)
    }

    private fun renderData(appState: AppState) = when (appState) {
        is AppState.Success -> {
            adapter.setWeather(appState.weatherData)
        }
        is AppState.Error -> {
            with(binding) {
                citiesFragmentRootLayout.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeather(location) })
            }
        }
        else -> {}
    }.also {
        if (appState == AppState.Loading) binding.citiesFragmentLoadingLayout.visibility = View.VISIBLE
        else binding.citiesFragmentLoadingLayout.visibility = View.GONE
    }

    private fun changeDataSet() {
        location = !location
        viewModel.getWeather(location)
        when (location) {
            Russia -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            World -> binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
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