package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import com.kozin.weatherkotlin.databinding.FragmentFutureWeatherBinding
import com.kozin.weatherkotlin.ui.adapter.RecyclerViewAdapter
import com.kozin.weatherkotlin.ui.viewModel.FutureWeatherViewModel
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_future_weather.*

class FutureWeatherFragment : Fragment() {

    private var _binding: FragmentFutureWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FutureWeatherViewModel
    private lateinit var adapter: RecyclerViewAdapter
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

        sessionManager = SessionManager(requireContext())

        setUpViewModel()
        setupUI()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(FutureWeatherViewModel::class.java)
    }

    private fun setupUI() {

        initRecyclerView()
        refreshData()

    }

    private fun initRecyclerView(){

        binding.rvRecyclerView.layoutManager = LinearLayoutManager(this.context)

    }


    private fun refreshData() {
        val args = sessionManager.fetchCityName()
        Log.i("MASAG", args!!)
        viewModel.getFutureWeatherByName(args, "en", "metric").observe(viewLifecycleOwner, {
            it?.let {resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        adapter = RecyclerViewAdapter(it.data?.data!!)
                        binding.rvRecyclerView.adapter = adapter
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