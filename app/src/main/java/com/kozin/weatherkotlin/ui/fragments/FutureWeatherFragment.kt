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

        setUpViewModel()
        setupUI()
    }

    private fun setUpViewModel() {

        viewModel = ViewModelProvider(this).get(FutureWeatherViewModel::class.java)
    }

    private fun setupUI() {

    }

    private fun refreshData() {
        viewModel.getFutureWeatherByName(autocompleteCityName, "en", "metric").observe(viewLifecycleOwner, {
            it?.let {resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        recyclerAdapter.setMovieListItems(response.body()!!)
                        rlWeather.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
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