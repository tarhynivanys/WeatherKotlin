package com.kozin.weatherkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class GoogleMapFragment : Fragment(), OnMapReadyCallback, OnMarkerClickListener {

    var map: GoogleMap? = null
    var marker1: Marker? = null

    private var _binding: FragmentGoogleMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GoogleMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoogleMapBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0

        map!!.setOnMarkerClickListener(this)

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
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }

}