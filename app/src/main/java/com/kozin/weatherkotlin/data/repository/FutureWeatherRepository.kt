package com.kozin.weatherkotlin.data.repository

import androidx.lifecycle.LiveData
import com.kozin.weatherkotlin.data.entities.ForecastEntity
import com.kozin.weatherkotlin.data.local.CurrentWeatherLocalDataSource
import com.kozin.weatherkotlin.data.local.FutureWeatherLocalDataSource
import com.kozin.weatherkotlin.data.remote.WeatherRemoteDataSource
import com.kozin.weatherkotlin.utils.performGetOperation

class FutureWeatherRepository
    (
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: FutureWeatherLocalDataSource
) {

    fun getFutureWeather(location: String, languageCode: String, metric: String) =
        performGetOperation(
            databaseQuery = {
                localDataSource.getFutureWeather()
            },

            networkCall = {
                remoteDataSource.getFutureWeather(location, languageCode, metric)
            },

            saveCallResult = {
                localDataSource.insertFutureWeather(it)
            }
        )

    fun getForecast(): LiveData<ForecastEntity> {
        return localDataSource.getFutureWeather()
    }
}