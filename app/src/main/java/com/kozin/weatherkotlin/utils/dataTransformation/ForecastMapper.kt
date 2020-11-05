package com.kozin.weatherkotlin.utils.dataTransformation

import com.kozin.weatherkotlin.data.response.future.InfoDay

class ForecastMapper : Mapper<List<InfoDay>, List<InfoDay>> {
    override fun mapFrom(type: List<InfoDay>): List<InfoDay> {
        val days = arrayListOf<String>()
        val mappedArray = arrayListOf<InfoDay>()

        type.forEachIndexed { _, listItem ->
            if (days.contains(listItem.dtTxt?.substringBefore(" ")).not()) // Add day to days
                listItem.dtTxt?.substringBefore(" ")?.let { days.add(it) }
        }

        days.forEach { day ->

            // Find min and max temp values each of the day
            val array = type.filter { it.dtTxt?.substringBefore(" ").equals(day) }

            val minTemp = array.minByOrNull { it.main?.tempMin ?: 0.0 }?.main?.tempMin
            val maxTemp = array.maxByOrNull { it.main?.tempMax ?: 0.0 }?.main?.tempMax

            array.forEach {
                it.main?.tempMin = minTemp // Set min
                it.main?.tempMax = maxTemp // Set max
                mappedArray.add(it) // add it to mappedArray
            }
        }

        return mappedArray
            .filter { it.dtTxt?.substringAfter(" ")?.substringBefore(":")?.toInt()!! >= 12 }
            .distinctBy { it.getDay() } // Eliminate same days
            .toList() // Return as list
    }
}


interface Mapper<R, D> {
    fun mapFrom(type: R): D
}
