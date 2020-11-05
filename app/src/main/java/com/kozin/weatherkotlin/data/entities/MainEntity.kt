package com.kozin.weatherkotlin.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.kozin.weatherkotlin.data.response.current.Main
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Main")
data class MainEntity(
    @ColumnInfo(name = "temp")
    val temp: Double?,

    @ColumnInfo(name = "feels_like")
    val feelsLike: Double?,

    @ColumnInfo(name = "temp_min")
    var tempMin: Double?,

    @ColumnInfo(name = "temp_max")
    var tempMax: Double?,

    @ColumnInfo(name = "pressure")
    val pressure: Double?,

    @ColumnInfo(name = "humidity")
    val humidity: Int?,

    @ColumnInfo(name = "sea_level")
    val seaLevel: Double?,

    @ColumnInfo(name = "grnd_level")
    val grndLevel: Double?
) : Parcelable {
    @Ignore
    constructor(main: Main?) : this(
        temp = main?.temp,
        feelsLike = main?.feelsLike,
        tempMin = main?.tempMin,
        tempMax = main?.tempMax,
        pressure = main?.pressure,
        humidity = main?.humidity,
        seaLevel = main?.seaLevel,
        grndLevel = main?.grndLevel,
    )

    fun getTempString(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String {
        return humidity.toString() + "°"
    }

    fun getFeelsLikeString(): String {
        return feelsLike.toString().substringBefore(".") + "°"

    }
}
