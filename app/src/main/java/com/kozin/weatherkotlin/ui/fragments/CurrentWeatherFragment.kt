package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.data.entities.CurrentWeatherEntity
import com.kozin.weatherkotlin.databinding.FragmentCurrentWeatherBinding
import com.kozin.weatherkotlin.ui.viewModel.CurrentWeatherViewModel
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.SessionManager
import com.kozin.weatherkotlin.utils.WeatherUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

class CurrentWeatherFragment : Fragment() {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var autocompleteCityName: String
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)

        Places.initialize(requireContext(), "AIzaSyATiSBmiHuJsMnVkwb0K2YDosHMNE6G6Jo")

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpViewModel()

        catchMapData()

        setupUI()

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
    }

    private fun catchMapData() {
        viewModel.fetchData().observe(viewLifecycleOwner, {
            it?.let {
                bindWeather(it)
                it.name?.let {
                    GlobalScope.launch {
                        sessionManager.saveCityName(it)
                    }
                }
            }
        })
    }

    private fun setupUI() {
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        sessionManager = SessionManager(requireContext())

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {

                MainScope().launch {
                    sessionManager.saveCityName(p0.name.toString())
                }

                autocompleteCityName = p0.name.toString()

                refreshData(autocompleteCityName)
            }

            override fun onError(p0: Status) {
                Timber.i("An error occurred: $p0")
            }
        })

    }

    private fun refreshData(cityName: String) {

        viewModel.setCurrentWeatherParams(WeatherUseCase.WeatherParams(cityName, "en", "metric"))
        viewModel.weather.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { bindWeather(it) }
                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    binding.rlWeather.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                Resource.Status.ERROR -> {
                    binding.rlWeather.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun bindWeather(weather: CurrentWeatherEntity) {
        binding.tvCountry.text = weather.sys?.country
        binding.tvCity.text = weather.name
        Glide.with(binding.imgIcon).load(weather.getCurrentWeatherIconValue())
            .into(binding.imgIcon)
        binding.tvTempValue.text = weather.main?.getTempString()
        binding.tvValueFeelsLike.text = weather.main?.getFeelsLikeString()
        binding.tvValueHumidity.text = weather.main?.getHumidityString()
        binding.tvValuePressure.text = weather.main?.pressure.toString()
        binding.rlWeather.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}