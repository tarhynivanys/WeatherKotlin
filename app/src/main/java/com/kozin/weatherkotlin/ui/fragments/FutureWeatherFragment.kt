package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.databinding.FragmentCurrentWeatherBinding
import com.kozin.weatherkotlin.databinding.FragmentFutureWeatherBinding
import com.kozin.weatherkotlin.ui.viewModel.CurrentWeatherViewModel
import com.kozin.weatherkotlin.ui.viewModel.FutureWeatherViewModel
import com.kozin.weatherkotlin.utils.Resource
import kotlinx.android.synthetic.main.fragment_current_weather.*

class FutureWeatherFragment : Fragment() {

    private var _binding: FragmentFutureWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FutureWeatherViewModel
    private lateinit var autocompleteCityName: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFutureWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Places.initialize(requireContext(), "AIzaSyATiSBmiHuJsMnVkwb0K2YDosHMNE6G6Jo")

        setUpViewModel()
        setupUI()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(FutureWeatherViewModel::class.java)
    }

    private fun setupUI() {
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                autocompleteCityName = p0.name.toString()
//                refreshData()
            }

            override fun onError(p0: Status) {

            }
        })

    }

    private fun refreshData() {
        viewModel.getFutureWeatherByName(autocompleteCityName, "en", "metric").observe(viewLifecycleOwner, {
            it?.let {resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        rlWeather.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        it.data?.let { currentWeather ->
                            binding.apply {
                                tvCity.text = currentWeather.data!!.name
                                tvCountry.text = currentWeather.data.sys.country
                                Glide.with(requireContext()).load(StringBuilder("http://openweathermap.org/img/wn/")
                                    .append(currentWeather.data.weather[0].icon).append(".png").toString()).into(imgIcon)
                                tvTempValue.text = currentWeather.data.main.temp.toString()
                                tvValueFeelsLike.text = currentWeather.data.main.feels_like.toString()
                                tvValueHumidity.text = currentWeather.data.main.humidity.toString()
                                tvValuePressure.text = currentWeather.data.main.pressure.toString()
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        rlWeather.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    Resource.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        rlWeather.visibility = View.GONE
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}