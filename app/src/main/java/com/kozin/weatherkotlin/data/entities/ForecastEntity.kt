package com.kozin.weatherkotlin.data.entities

import android.os.Parcelable
import androidx.room.*
import com.kozin.weatherkotlin.data.response.future.FutureWeatherEntry
import com.kozin.weatherkotlin.data.response.future.InfoDay
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Forecast")
data class ForecastEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @Embedded
    var city: CityEntity?,

    @ColumnInfo(name = "list")
    var list: List<InfoDay>?
) : Parcelable {

    @Ignore
    constructor(forecastResponse: FutureWeatherEntry) : this(
        id = 0,
        city = forecastResponse.city?.let { CityEntity(it) },
        list = forecastResponse.list
    )
}
