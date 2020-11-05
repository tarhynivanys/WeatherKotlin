package com.kozin.weatherkotlin.data.response.future

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class FutureWeatherEntry(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<InfoDay>?,
    val message: Int?
) : Parcelable