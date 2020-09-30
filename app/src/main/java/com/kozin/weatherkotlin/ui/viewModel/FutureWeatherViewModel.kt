package com.kozin.weatherkotlin.ui.viewModel

import androidx.lifecycle.ViewModel
import com.kozin.weatherkotlin.data.repository.WeatherRepository
import com.kozin.weatherkotlin.remote.WeatherRemoteDataSource
import com.kozin.weatherkotlin.remote.retrofit.ApiInterface

class FutureWeatherViewModel() : ViewModel() {

    private val apiService: ApiInterface = ApiInterface()
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource(apiService)
    private val repository: WeatherRepository = WeatherRepository(remoteDataSource)


}