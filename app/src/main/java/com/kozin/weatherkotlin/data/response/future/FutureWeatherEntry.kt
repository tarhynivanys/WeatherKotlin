package com.kozin.weatherkotlin.data.response.future

data class FutureWeatherEntry(
    val city: City,
    val cnt: Double,
    val cod: String,
    val list: List<FutureList>,
    val message: Double
)