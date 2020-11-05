package com.kozin.weatherkotlin.data.response.future

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kozin.weatherkotlin.data.response.current.Weather
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
data class InfoDay(
    @Json(name = "clouds")
    val clouds: Clouds?,
    @Json(name = "dt")
    val dt: Long?,
    @SerializedName("dt_txt")
    val dtTxt: String?,
    @Json(name = "main")
    val main: Main?,
    @Json(name = "pop")
    val pop: Double?,
    @Json(name = "rain")
    val rain: Rain?,
    @Json(name = "sys")
    val sys: Sys?,
    @Json(name = "visibility")
    val visibility: Int?,
    @Json(name = "weather")
    val weather: List<Weather?>?,
    @Json(name = "wind")
    val wind: Wind?
) : Parcelable {

    private fun getWeatherItem(): Weather? {
        return weather?.first()
    }
    fun getWeatherItemValue(): String? {
        return StringBuilder("http://openweathermap.org/img/wn/")
            .append(getWeatherItem()?.icon)
            .append(".png")
            .toString()
    }

    fun getDay(): String? {
        return dt?.let {
            getDateTime(it)?.getDisplayName(TextStyle.FULL, Locale.getDefault()) }
    }

    private fun getDateTime(s: Long): DayOfWeek? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s * 1000)
            val formattedDate = sdf.format(netDate)

            LocalDate.of(
                formattedDate.substringAfterLast("/").toInt(),
                formattedDate.substringAfter("/").take(2).toInt(),
                formattedDate.substringBefore("/").toInt()
            )
                .dayOfWeek
        } catch (e: Exception) {
            e.printStackTrace()
            DayOfWeek.MONDAY
        }
    }

    fun getHourOfDay(): String {
        return dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
    }

    fun getDayDate(): String {
        return dtTxt?.substringBefore(" ") ?: ""
    }
}