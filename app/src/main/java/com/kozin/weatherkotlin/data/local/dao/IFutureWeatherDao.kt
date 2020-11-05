package com.kozin.weatherkotlin.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kozin.weatherkotlin.data.entities.CurrentWeatherEntity
import com.kozin.weatherkotlin.data.entities.ForecastEntity

@Dao
interface IFutureWeatherDao {

    @Query("SELECT * FROM Forecast")
    fun getFutureWeather(): LiveData<ForecastEntity>

    @Query("SELECT * FROM Forecast ORDER BY abs(lat-:lat) AND abs(lon-:lon) LIMIT 1")
    fun getFutureWeatherByCoord(lat: Double, lon: Double): LiveData<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFutureWeather(forecastEntity: ForecastEntity)

    @Transaction
    fun deleteAndInsert(forecastEntity: ForecastEntity){
        deleteFutureWeather()
        insertFutureWeather(forecastEntity)
    }

    @Update
    fun updateFutureWeather(forecastEntity: ForecastEntity)

    @Delete
    fun deleteForecast(forecast: ForecastEntity)

    @Query("DELETE FROM Forecast")
    fun deleteFutureWeather()

    @Query("SELECT count(*) FROM Forecast")
    fun getCount(): Int

}