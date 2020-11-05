package com.kozin.weatherkotlin.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kozin.weatherkotlin.data.entities.CurrentWeatherEntity

@Dao
interface ICurrentWeatherDao {

    @Query("SELECT * FROM CurrentWeather")
    fun getCurrentWeather(): LiveData<CurrentWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Transaction
    fun deleteAndInsert(currentWeatherEntity: CurrentWeatherEntity){
        deleteCurrentWeather()
        insertCurrentWeather(currentWeatherEntity)
    }

    @Query ("DELETE FROM CurrentWeather")
    fun deleteCurrentWeather()

    @Query ("SELECT count(*) FROM CurrentWeather")
    fun getCount(): Int

}