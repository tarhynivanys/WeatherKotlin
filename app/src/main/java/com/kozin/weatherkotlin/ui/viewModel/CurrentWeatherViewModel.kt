package com.kozin.weatherkotlin.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.kozin.weatherkotlin.data.entities.CurrentWeatherEntity
import com.kozin.weatherkotlin.data.local.CurrentWeatherLocalDataSource
import com.kozin.weatherkotlin.data.local.db.WeatherDatabase
import com.kozin.weatherkotlin.data.repository.CurrentWeatherRepository
import com.kozin.weatherkotlin.data.remote.WeatherRemoteDataSource
import com.kozin.weatherkotlin.data.remote.retrofit.ApiInterface
import com.kozin.weatherkotlin.utils.Resource
import com.kozin.weatherkotlin.utils.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CurrentWeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService: ApiInterface = ApiInterface()
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource(apiService)
    private val weatherDao = WeatherDatabase.getDatabase(application).currentDao()
    private val localDataSource = CurrentWeatherLocalDataSource(weatherDao)
    private val repository = CurrentWeatherRepository(remoteDataSource, localDataSource)

    private val _params: MutableLiveData<WeatherUseCase.WeatherParams> = MutableLiveData()

    fun fetchData(): LiveData<CurrentWeatherEntity> = localDataSource.getCurrentWeather()

    fun setCurrentWeatherParams(params: WeatherUseCase.WeatherParams) {
        if (_params.value == params)
            return
        _params.postValue(params)
    }

    private val _weather = _params.switchMap {
        repository.getCurrentWeatherByName(it._location, it._languageCode, it._units)
    }

    val weather: LiveData<Resource<CurrentWeatherEntity>> =_weather

}