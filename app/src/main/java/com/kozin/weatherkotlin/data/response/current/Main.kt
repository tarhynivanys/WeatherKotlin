package com.kozin.weatherkotlin.data.response.current

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Main(

    val temp: Double?,

    @SerializedName("feels_like")
    val feelsLike: Double?,

    @SerializedName("temp_min")
    var tempMin: Double?,

    @SerializedName("temp_max")
    var tempMax: Double?,

    val pressure: Double?,

    val humidity: Int?,

    @SerializedName("sea_level")
    val seaLevel: Double?,

    @SerializedName("grnd_level")
    val grndLevel: Double?

) : Parcelable