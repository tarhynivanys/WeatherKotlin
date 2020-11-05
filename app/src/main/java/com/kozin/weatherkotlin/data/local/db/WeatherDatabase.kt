package com.kozin.weatherkotlin.data.local.db

import android.content.Context
import androidx.room.*
import com.kozin.weatherkotlin.data.entities.CurrentWeatherEntity
import com.kozin.weatherkotlin.data.entities.ForecastEntity
import com.kozin.weatherkotlin.data.local.dao.ICurrentWeatherDao
import com.kozin.weatherkotlin.data.local.dao.IFutureWeatherDao
import com.kozin.weatherkotlin.data.response.current.CurrentWeatherEntry
import com.kozin.weatherkotlin.utils.dataTransformation.DataConverter
import java.security.AccessControlContext

@Database(
    entities = [CurrentWeatherEntity::class, ForecastEntity::class],
    version = 2, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun currentDao(): ICurrentWeatherDao
    abstract fun futureDao(): IFutureWeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase =
            instance ?: synchronized(this)
            { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext,
                WeatherDatabase::class.java,
            "weather.db")
                .fallbackToDestructiveMigration()
                .build()
    }

}