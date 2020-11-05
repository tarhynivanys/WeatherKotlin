package com.kozin.weatherkotlin.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.kozin.weatherkotlin.data.entities.CurrentWeatherEntity
import com.kozin.weatherkotlin.data.local.CurrentWeatherLocalDataSource
import com.kozin.weatherkotlin.data.local.FutureWeatherLocalDataSource
import com.kozin.weatherkotlin.data.local.db.WeatherDatabase
import com.kozin.weatherkotlin.data.repository.CurrentWeatherRepository
import com.kozin.weatherkotlin.data.remote.WeatherRemoteDataSource
import com.kozin.weatherkotlin.data.remote.retrofit.ApiInterface
import com.kozin.weatherkotlin.data.repository.FutureWeatherRepository
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class GoogleMapViewModel(application: Application) : AndroidViewModel(application)  {

    private val apiService: ApiInterface = ApiInterface()
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource(apiService)
    private val weatherDao = WeatherDatabase.getDatabase(application).currentDao()
    private val localDataSource = CurrentWeatherLocalDataSource(weatherDao)
    private val repository: CurrentWeatherRepository = CurrentWeatherRepository(remoteDataSource, localDataSource)

    private val _params: MutableLiveData<WeatherUseCase.WeatherCoordParams> = MutableLiveData()

    fun setCurrentWeatherParams(params: WeatherUseCase.WeatherCoordParams) {
        if (_params.value == params)
            return
        _params.postValue(params)
    }

    private val _weather = _params.switchMap {
        repository.getCurrentWeatherByLatLng(it._latitude, it._longitude, it._languageCode, it._units)
    }

    val weather: LiveData<Resource<CurrentWeatherEntity>> = _weather

}