package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
import com.kozin.weatherkotlin.ui.viewModel.CurrentWeatherViewModel
import com.kozin.weatherkotlin.ui.viewModel.GoogleMapViewModel
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_current_weather.*

class GoogleMapFragment : Fragment(), OnMapReadyCallback, OnMarkerClickListener {

    var map: GoogleMap? = null
    var marker1: Marker? = null

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
            map = p0

            map!!.setOnMapClickListener { latLng ->
                map!!.clear()
                marker1 = map!!.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(latLng.latitude.toString() + " : " + latLng.longitude)
                        .snippet("This is my spot!")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

                getCityName()

//                    Log.i("MAS", marker1?.position?.latitude.toString() + " " +
//                            marker1?.position?.longitude.toString())
//                    viewModel.getCurrentWeatherByLatLng(marker1?.position?.latitude.toString(),
//                        marker1?.position?.longitude.toString(),
//                        "en", "metric")
//                        .observe(viewLifecycleOwner, {
//                            it?.let {resource ->
//                                when (resource.status) {
//                                    Resource.Status.SUCCESS -> {
//                                        it.data?.let { currentWeather ->
//                                            var cityName: String = currentWeather.data!!.name
//                                            sessionManager.saveCityLatLng(cityName)
//                                            Log.i("MAS", cityName)
//                                        }
//                                    }
//                                    Resource.Status.ERROR -> {
//                                        Log.i("MAS", "OSHIBKA")
//                                    }
//                                    Resource.Status.LOADING -> {
//                                        Log.i("MAS", "LOADING")
//                                    }
//                                }
//                            }
//                        })


                }


            }
        }

    override fun onMarkerClick(p0: Marker?): Boolean = false

    private fun getCityName(){
        Log.i("MAS", marker1?.position?.latitude.toString() + " " +
                marker1?.position?.longitude.toString())
        viewModel.getCurrentWeatherByLatLng(marker1?.position?.latitude.toString(),
            marker1?.position?.longitude.toString(),
            "en", "metric")
            .observe(viewLifecycleOwner, {
                it?.let {resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            it.data?.let { currentWeather ->
                                var cityName: String = currentWeather.data!!.name
                                sessionManager.saveCityLatLng(cityName)
                                Log.i("MAS", cityName)
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

//    override fun onMarkerClick(p0: Marker?): Boolean {
//        if (p0?.equals(marker1)!!){
//            Log.i("MAS", p0?.position?.latitude.toString() + " " +
//                    p0?.position?.longitude.toString())
//        }else{
//            Log.i("MAS", "FUK U")
//        }
//
//        when(p0 == marker1){ true -> {
//            Log.i("MAS", marker1?.position?.latitude.toString() + " " +
//                    marker1?.position?.longitude.toString())
//            viewModel.getCurrentWeatherByLatLng(marker1?.position?.latitude.toString(),
//                marker1?.position?.longitude.toString(),
//                "en", "metric")
//                .observe(viewLifecycleOwner, {
//                    it?.let {resource ->
//                        when (resource.status) {
//                            Resource.Status.SUCCESS -> {
//                                it.data?.let { currentWeather ->
//                                    var cityName: String = currentWeather.data!!.name
//                                    sessionManager.saveCityLatLng(cityName)
//                                    Log.i("MAS", cityName)
//                                }
//                            }
//                            Resource.Status.ERROR -> {
//                                Log.i("MAS", "OSHIBKA")
//                            }
//                            Resource.Status.LOADING -> {
//                                Log.i("MAS", "LOADING")
//                            }
//                        }
//                    }
//                })
//        }
//          false -> {
//              Log.i("TRAP", "MISS")
//
//          }
//        }

//        Log.i("MAS", marker1?.position?.latitude.toString() + " " +
//            marker1?.position?.longitude.toString())



//        return false
//    }

//    private fun refreshData() {
//        viewModel.getCurrentWeatherByLatLng(marker1?.position?.latitude.toString(),
//            marker1?.position?.longitude.toString(),
//            "en", "metric")
//            .observe(viewLifecycleOwner, {
//            it?.let {resource ->
//                when (resource.status) {
//                    Resource.Status.SUCCESS -> {
//                        it.data?.let { currentWeather ->
//                            var cityName: String = currentWeather.data!!.name
//                            sessionManager.saveCityLatLng(cityName)
//
//                        }
//                    }
//                    Resource.Status.ERROR -> {
//
//                    }
//                    Resource.Status.LOADING -> {
//
//                    }
//                }
//            }
//        })
//    }

//}