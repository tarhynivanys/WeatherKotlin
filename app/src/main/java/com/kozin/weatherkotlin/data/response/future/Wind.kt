package com.kozin.weatherkotlin.data.response.future

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)

data class Wind(
    val deg: Double?,
    val speed: Double?
) : Parcelable