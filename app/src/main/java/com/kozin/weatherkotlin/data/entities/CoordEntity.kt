package com.kozin.weatherkotlin.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.kozin.weatherkotlin.data.response.future.Coord
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Coord")
data class CoordEntity(
    @ColumnInfo(name = "lon")
    val lon: Double?,
    @ColumnInfo(name = "lat")
    val lat: Double?
) : Parcelable {
    @Ignore
    constructor(coord: Coord) : this(
        lon = coord.lon,
        lat = coord.lat
    )
}
