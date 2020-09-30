package com.kozin.weatherkotlin.data.repository

import com.kozin.weatherkotlin.remote.WeatherRemoteDataSource

class WeatherRepository(private val remoteDataSource: WeatherRemoteDataSource) {

    suspend fun getWeather(location: String, languageCode: String, metric: String)
            = remoteDataSource.getCurrentWeather(location, languageCode, metric)

}