package com.kozin.weatherkotlin.ui.viewModel

import androidx.lifecycle.liveData
import com.kozin.weatherkotlin.data.repository.WeatherRepository
import com.kozin.weatherkotlin.remote.WeatherRemoteDataSource
import com.kozin.weatherkotlin.remote.retrofit.ApiInterface
import com.kozin.weatherkotlin.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class GoogleMapViewModel {

    private val apiService: ApiInterface = ApiInterface()
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource(apiService)
    private val repository: WeatherRepository = WeatherRepository(remoteDataSource)

    fun getCurrentWeatherByLatLng(lat: String, lon: String, languageCode: String, metric: String) = liveData(
        Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCurrentWeatherByLatLng(lat, lon, languageCode, metric)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}