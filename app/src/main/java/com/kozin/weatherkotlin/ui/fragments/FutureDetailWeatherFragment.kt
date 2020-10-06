package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kozin.weatherkotlin.databinding.FragmentFutureDetailWeatherBinding
import com.kozin.weatherkotlin.ui.adapter.DetailWeatherAdapter
import com.kozin.weatherkotlin.ui.viewModel.FutureDetailWeatherViewModel
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_future_weather.*

class FutureDetailWeatherFragment : Fragment() {

    private var _binding: FragmentFutureDetailWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FutureDetailWeatherViewModel
    private lateinit var adapter: DetailWeatherAdapter
    private lateinit var sessionManager: SessionManager

    private var args: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFutureDetailWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sessionManager = SessionManager(requireContext())

        setUpViewModel()
        setupUI()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(FutureDetailWeatherViewModel::class.java)
    }

    private fun setupUI() {
        initRecyclerView()

        args = sessionManager.fetchCityName()
        args?.let { refreshData(args!!) }

    }

    private fun initRecyclerView(){

        binding.rvDetailRecyclerView.layoutManager = LinearLayoutManager(this.context)

    }


    private fun refreshData(cityName: String) {

        viewModel.getFutureWeatherByName(cityName, "en", "metric").observe(viewLifecycleOwner, {
            it?.let {resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        //adapter = DetailWeatherAdapter(it.data?.data!!)

                        adapter = DetailWeatherAdapter(it.data?.data!!, arguments?.getString("key"))

                        binding.rvDetailRecyclerView.adapter = adapter
                        futureProgressBar.visibility = View.GONE
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(this.context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        futureProgressBar.visibility = View.GONE
                    }
                    Resource.Status.LOADING -> {

                        futureProgressBar.visibility = View.VISIBLE

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