package com.kozin.weatherkotlin.utils.dataTransformation

import androidx.room.TypeConverter
import com.kozin.weatherkotlin.data.response.future.InfoDay
import com.kozin.weatherkotlin.data.response.current.Weather
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object DataConverter {

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<InfoDay>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, InfoDay::class.java)
        val adapter = moshi.adapter<List<InfoDay>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<InfoDay>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, InfoDay::class.java)
        val adapter = moshi.adapter<List<InfoDay>>(type)
        return adapter.toJson(objects)
    }

    @TypeConverter
    @JvmStatic
    fun weatherStringToList(data: String?): List<Weather>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Weather::class.java)
        val adapter = moshi.adapter<List<Weather>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun weatherListToString(objects: List<Weather>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Weather::class.java)
        val adapter = moshi.adapter<List<Weather>>(type)
        return adapter.toJson(objects)
    }
}
