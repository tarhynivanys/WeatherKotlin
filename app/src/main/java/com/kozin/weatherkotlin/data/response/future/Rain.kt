package com.kozin.weatherkotlin.data.response.future

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Rain(
    @SerializedName("3h")
    val h: Double?
) : Parcelable