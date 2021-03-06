package com.example.forecastmvvm.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(

    val feelslike: Double,

    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val precip: Double,

    val temperature: Double,
    @SerializedName("uv_index")
    val uvIndex: Double,
    val visibility: Double,
    @SerializedName("weather_code")
    val weatherCode: Double,

////    @TypeConverters(com.example.forecastmvvm.data.db.entity.TypeConverter::class)
//    @SerializedName("weather_descriptions")
//    val weatherDescriptions: List<String>,
////    @TypeConverters(com.example.forecastmvvm.data.db.entity.TypeConverter::class)
//    @SerializedName("weather_icons")
//    val weatherIcons: List<String>,


    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}