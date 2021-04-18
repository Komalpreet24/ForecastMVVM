package com.example.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.forecastmvvm.data.db.entity.CURRENT_WEATHER_ID
import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.db.entity.TypeConverter
import com.example.forecastmvvm.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.forecastmvvm.data.db.unitlocalized.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
//    @TypeConverters(TypeConverter::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

//    @TypeConverters(TypeConverter::class)
    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

//    @TypeConverters(TypeConverter::class)
    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

}