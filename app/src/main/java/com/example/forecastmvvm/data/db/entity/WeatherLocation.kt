package com.example.forecastmvvm.data.db.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime
import java.time.Instant
import java.time.ZoneId


const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    val country: String,
    val lat: Double,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("timezone_id")
    val timezoneId: String,
    @SerializedName("utc_offset")
    val utcOffset: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID

    val zonedDateTime: ZonedDateTime
    @RequiresApi(Build.VERSION_CODES.O)
    get() {
        val instant = org.threeten.bp.Instant.ofEpochSecond(localtimeEpoch)
        val timezoneId = org.threeten.bp.ZoneId.of(timezoneId)
        return ZonedDateTime.ofInstant(instant, timezoneId)
    }

}