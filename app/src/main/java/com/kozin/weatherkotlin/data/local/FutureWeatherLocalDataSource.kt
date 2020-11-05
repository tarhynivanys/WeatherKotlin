package com.kozin.weatherkotlin.data.local

import com.kozin.weatherkotlin.data.entities.ForecastEntity
import com.kozin.weatherkotlin.data.local.dao.IFutureWeatherDao
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import com.kozin.weatherkotlin.utils.BaseDataSource

class FutureWeatherLocalDataSource(private val futureDao: IFutureWeatherDao) : BaseDataSource() {

    fun getFutureWeather() = futureDao.getFutureWeather()

    fun insertFutureWeather(futureWeatherEntry: FutureWeatherEntry) = futureDao.deleteAndInsert(
        ForecastEntity(futureWeatherEntry)
    )

}