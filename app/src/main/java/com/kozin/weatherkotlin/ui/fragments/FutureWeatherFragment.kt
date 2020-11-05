package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kozin.weatherkotlin.data.response.future.InfoDay
import com.kozin.weatherkotlin.databinding.FragmentFutureWeatherBinding
import com.kozin.weatherkotlin.ui.adapter.ForecastAdapter
import com.kozin.weatherkotlin.ui.viewModel.FutureWeatherViewModel
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.SessionManager
import com.kozin.weatherkotlin.utils.WeatherUseCase
import com.kozin.weatherkotlin.utils.dataTransformation.ForecastMapper

class FutureWeatherFragment : Fragment(), ForecastAdapter.WeatherItemListener {

    private var _binding: FragmentFutureWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FutureWeatherViewModel
    private lateinit var adapter: ForecastAdapter
    private lateinit var sessionManager: SessionManager

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
        refreshData()
    }

    private fun setUpViewModel() {
        sessionManager = SessionManager(requireContext())
        viewModel = ViewModelProvider(this).get(FutureWeatherViewModel::class.java)
        adapter = ForecastAdapter(null, this)
        binding.rvRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun refreshData() {

        val cityName: String = sessionManager.fetchCityName()!!

        viewModel.setFutureWeatherParams(WeatherUseCase.WeatherParams(cityName, "en", "metric"))
        viewModel.forecast.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {

                    it.data?.let { forecastEntity ->
                        val mappedList = forecastEntity.list?.let { dayInfo ->
                            ForecastMapper().mapFrom(dayInfo)
                        }
                        adapter = ForecastAdapter(mappedList, this)
                    }

                    binding.rvRecyclerView.adapter = adapter
                    binding.rvRecyclerView.visibility = View.VISIBLE
                    binding.futureProgressBar.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    binding.rvRecyclerView.visibility = View.GONE
                    binding.futureProgressBar.visibility = View.VISIBLE
                }

                Resource.Status.ERROR -> {
                    binding.futureProgressBar.visibility = View.GONE
                    binding.rvRecyclerView.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onClickedWeather(currentDay: InfoDay) {
        findNavController().navigate(
            FutureWeatherFragmentDirections.actionFutureWeatherFragmentToFutureDetailWeatherFragment(
                currentDay
            )
        )
    }

}