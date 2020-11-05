package com.kozin.weatherkotlin.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.kozin.weatherkotlin.data.entities.ForecastEntity
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

class FutureWeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService: ApiInterface = ApiInterface()
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource(apiService)
    private val weatherDao = WeatherDatabase.getDatabase(application).futureDao()
    private val localDataSource = FutureWeatherLocalDataSource(weatherDao)
    private val repository: FutureWeatherRepository = FutureWeatherRepository(remoteDataSource, localDataSource)

    private val _params: MutableLiveData<WeatherUseCase.WeatherParams> = MutableLiveData()

    fun setFutureWeatherParams(params: WeatherUseCase.WeatherParams) {
        if (_params.value == params)
            return
        _params.postValue(params)
    }

    private val forecastViewState = _params.switchMap {
        repository.getFutureWeather(it._location, it._languageCode, it._units)
    }

    val forecast: LiveData<Resource<ForecastEntity>> = forecastViewState

}