package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kozin.weatherkotlin.data.response.future.InfoDay
import com.kozin.weatherkotlin.databinding.FragmentFutureDetailWeatherBinding
import com.kozin.weatherkotlin.ui.adapter.DetailWeatherAdapter
import com.kozin.weatherkotlin.ui.viewModel.FutureDetailWeatherViewModel
import com.kozin.weatherkotlin.utils.SessionManager

class FutureDetailWeatherFragment : Fragment() {

    private var _binding: FragmentFutureDetailWeatherBinding? = null
    private val binding get() = _binding!!

    //private lateinit var viewModel: FutureDetailWeatherViewModel
    private val viewModel: FutureDetailWeatherViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    private val args: FutureDetailWeatherFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFutureDetailWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupUI()
    }

    private fun setupUI() {

        viewModel.weatherItem.set(args.infoDay)
        viewModel.selectedDayDate = args.infoDay.dtTxt?.substringBefore(" ")
        viewModel.getForecast().observe(viewLifecycleOwner, {
            viewModel.selectedDayForecastLiveData.postValue(it.list?.filter { infoDay ->
                infoDay.dtTxt?.substringBefore(" ") == viewModel.selectedDayDate
            })
        })

        viewModel.selectedDayForecastLiveData.observe(viewLifecycleOwner, {
            initWeatherHourOfDayAdapter(it)
        })

        val item = viewModel.weatherItem.get()
        binding.textViewTemp.text = item?.main?.getTempString()
        binding.textViewDayOfWeek.text = item?.getDay()
        Glide.with(binding.imageViewForecastIcon)
            .load(item?.getWeatherItemValue())
            .into(binding.imageViewForecastIcon)
        binding.textViewMinTemp.text = item?.main?.getTempMinString()
        binding.textViewMaxTemp.text = item?.main?.getTempMaxString()

        binding.fabClose.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun initWeatherHourOfDayAdapter(list: List<InfoDay>?) {
        val adapter = DetailWeatherAdapter(list)

        binding.recyclerViewHourOfDay.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}