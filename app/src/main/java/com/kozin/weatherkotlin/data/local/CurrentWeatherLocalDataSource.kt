package com.kozin.weatherkotlin.data.local

import com.kozin.weatherkotlin.data.entities.CurrentWeatherEntity
import com.kozin.weatherkotlin.data.local.dao.ICurrentWeatherDao
import com.kozin.weatherkotlin.data.response.current.CurrentWeatherEntry
import com.kozin.weatherkotlin.utils.BaseDataSource

class CurrentWeatherLocalDataSource(private val currentWeatherDao: ICurrentWeatherDao): BaseDataSource(){
    fun getCurrentWeather() = currentWeatherDao.getCurrentWeather()
    fun insertCurrentWeather(currentWeatherEntry: CurrentWeatherEntry) = currentWeatherDao.deleteAndInsert(
        CurrentWeatherEntity(currentWeatherEntry)
    )

    fun deleteCurrentWeather() = currentWeatherDao.deleteCurrentWeather()
}