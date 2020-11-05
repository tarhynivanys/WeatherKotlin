package com.kozin.weatherkotlin.ui.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.kozin.weatherkotlin.data.entities.ForecastEntity
import com.kozin.weatherkotlin.data.local.FutureWeatherLocalDataSource
import com.kozin.weatherkotlin.data.local.db.WeatherDatabase
import com.kozin.weatherkotlin.data.repository.CurrentWeatherRepository
import com.kozin.weatherkotlin.data.remote.WeatherRemoteDataSource
import com.kozin.weatherkotlin.data.remote.retrofit.ApiInterface
import com.kozin.weatherkotlin.data.repository.FutureWeatherRepository
import com.kozin.weatherkotlin.data.response.future.InfoDay
import com.kozin.weatherkotlin.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FutureDetailWeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService: ApiInterface = ApiInterface()
    private val remoteDataSource: WeatherRemoteDataSource = WeatherRemoteDataSource(apiService)
    private val weatherDao = WeatherDatabase.getDatabase(application).futureDao()
    private val forecastLocalDataSource = FutureWeatherLocalDataSource(weatherDao)
    private val repository = FutureWeatherRepository(remoteDataSource, forecastLocalDataSource)

    var weatherItem: ObservableField<InfoDay> = ObservableField()
    var selectedDayDate: String? = null
    var selectedDayForecastLiveData: MutableLiveData<List<InfoDay>> = MutableLiveData()

    fun getForecast(): LiveData<ForecastEntity> {
        return repository.getForecast()
    }

}