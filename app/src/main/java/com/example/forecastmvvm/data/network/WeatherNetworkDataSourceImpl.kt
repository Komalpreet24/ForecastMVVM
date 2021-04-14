package com.example.forecastmvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import com.example.forecastmvvm.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiService: ApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() =  _downloadedCurrentWeather //To change initializer of created properties use File | Settings | File Templates.

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        //To change body of created functions use File | Settings | File Templates.
        try{
            val fetchedCurrentWeather = apiService
                .getCurrentWeather(location , languageCode).await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity","No Internet Connection", e)
        }
    }
}