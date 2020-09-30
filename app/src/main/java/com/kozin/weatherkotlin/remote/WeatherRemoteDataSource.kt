package com.kozin.weatherkotlin.remote

import com.kozin.weatherkotlin.remote.retrofit.ApiInterface

class WeatherRemoteDataSource(private val weatherService: ApiInterface): BaseDataSource() {
    suspend fun getCurrentWeather(location: String, languageCode: String, metric: String)
            = getResult { weatherService.getCurrentWeather(location, languageCode, metric) }

    suspend fun getFutureWeather(location: String, languageCode: String, metric: String)
            = getResult { weatherService.getFutureWeather(location, languageCode, metric) }
}