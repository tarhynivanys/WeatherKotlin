package com.kozin.weatherkotlin.data.repository

import com.kozin.weatherkotlin.data.local.CurrentWeatherLocalDataSource
import com.kozin.weatherkotlin.data.remote.WeatherRemoteDataSource
import com.kozin.weatherkotlin.utils.performGetOperation

class CurrentWeatherRepository(private val remoteDataSource: WeatherRemoteDataSource,
                               private val localDataSource: CurrentWeatherLocalDataSource) {

    fun getCurrentWeatherByName(location: String, languageCode: String, metric: String) = performGetOperation(
        databaseQuery = { localDataSource.getCurrentWeather() },
        networkCall = { remoteDataSource.getCurrentWeatherByCityName(location, languageCode, metric) },
        saveCallResult = { localDataSource.insertCurrentWeather(it) }
    )

    fun getCurrentWeatherByLatLng(lat: String, lon: String, languageCode: String, metric: String) = performGetOperation(
        databaseQuery = { localDataSource.getCurrentWeather() },
        networkCall = { remoteDataSource.getCurrentWeatherByLatLng(lat, lon, languageCode, metric) },
        saveCallResult = { localDataSource.insertCurrentWeather(it) }
    )

}