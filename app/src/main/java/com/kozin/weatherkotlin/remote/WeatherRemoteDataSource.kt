package com.kozin.weatherkotlin.remote

import com.kozin.weatherkotlin.remote.retrofit.ApiInterface

class WeatherRemoteDataSource(private val weatherService: ApiInterface): BaseDataSource() {
    suspend fun getCurrentWeatherByCityName(location: String, languageCode: String, metric: String)
            = getResult { weatherService.getCurrentWeatherByCityName(location, languageCode, metric) }

    suspend fun getFutureWeather(location: String, languageCode: String, metric: String)
            = getResult { weatherService.getFutureWeatherByCityName(location, languageCode, metric) }

    suspend fun getCurrentWeatherByLatLng(lat: String, lon: String, languageCode: String, metric: String)
            = getResult { weatherService.getCurrentWeatherByLatLng(lat, lon, languageCode, metric) }
}