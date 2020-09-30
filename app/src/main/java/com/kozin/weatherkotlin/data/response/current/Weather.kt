package com.kozin.weatherkotlin.data.response.current

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)