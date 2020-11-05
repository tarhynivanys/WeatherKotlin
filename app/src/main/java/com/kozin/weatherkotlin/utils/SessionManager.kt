package com.kozin.weatherkotlin.utils

import android.content.Context
import android.content.SharedPreferences
import com.kozin.weatherkotlin.R

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val CITY_NAME = "city_name"
        const val CITY_LAT_LNG = "city_lat_lng"
    }

    fun saveCityName(keys: String) {
        val editor = prefs.edit()
        editor.putString(CITY_NAME, keys)
        editor.apply()
    }

    fun fetchCityName(): String? {
        return prefs.getString(CITY_NAME, null)
    }

    fun deleteCityName()
    {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun saveCityLatLng(keys: String) {
        val editor = prefs.edit()
        editor.putString(CITY_LAT_LNG, keys)
        editor.apply()
    }

    fun fetchCityLatLng(): String? {
        return prefs.getString(CITY_LAT_LNG, null)
    }

}