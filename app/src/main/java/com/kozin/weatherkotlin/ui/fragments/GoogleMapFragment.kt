package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kozin.weatherkotlin.R
import com.kozin.weatherkotlin.databinding.FragmentGoogleMapBinding
import com.kozin.weatherkotlin.ui.viewModel.GoogleMapViewModel
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.SessionManager

class GoogleMapFragment : Fragment(), OnMapReadyCallback, OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var marker1: Marker

    private var _binding: FragmentGoogleMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GoogleMapViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoogleMapBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(GoogleMapViewModel::class.java)
    }

    override fun onMapReady(p0: GoogleMap?) {
        setUpViewModel()
        p0?.apply {
            p0.setOnMapClickListener { latLng ->
                p0.clear()
                marker1 = p0.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(latLng.latitude.toString() + " : " + latLng.longitude)
                        .snippet("This is my spot!")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                p0.setOnMarkerClickListener(this@GoogleMapFragment)

                }


            }
        }

    override fun onMarkerClick(p0: Marker?): Boolean {
        getCityName()
        return false
    }

    private fun getCityName(){
        viewModel.getCurrentWeatherByLatLng(marker1.position?.latitude.toString(),
            marker1.position?.longitude.toString(),
            "en", "metric")
            .observe(viewLifecycleOwner, {
                it?.let {resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            it.data?.let { currentWeather ->
                                val cityName: String = currentWeather.data!!.name
                                sessionManager.saveCityLatLng(cityName)
                                Log.i("MAS", cityName)
                                findNavController().navigate(R.id.action_googleMapFragment_to_currentWeatherFragment2)
                            }
                        }
                        Resource.Status.ERROR -> {
                            Log.i("MAS", "OSHIBKA")
                        }
                        Resource.Status.LOADING -> {
                            Log.i("MAS", "LOADING")
                        }
                    }
                }
            })
    }
}
