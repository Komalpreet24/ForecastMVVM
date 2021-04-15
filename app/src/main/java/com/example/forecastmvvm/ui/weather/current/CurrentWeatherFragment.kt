package com.hypheno.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.forecastmvvm.R
import com.example.forecastmvvm.data.network.ApiService
import com.example.forecastmvvm.data.network.ConnectivityInterceptorImpl
import com.example.forecastmvvm.data.network.WeatherNetworkDataSource
import com.example.forecastmvvm.data.network.WeatherNetworkDataSourceImpl
import com.example.forecastmvvm.internal.glide.GlideApp
import com.example.forecastmvvm.ui.base.ScopedFragment
import com.example.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.common.internal.GmsLogger
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(),KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner , Observer{
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Los Angeles")
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition("Weather Description")
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
//                .load("https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0004_black_low_cloud.png")
                .load(R.drawable.ic_weather_sunny)
                .into(imageView_condition_icon)

        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric : String, imperial : String) : String {
        return if(viewModel.isMetric) metric else imperial
    }

    private fun updateVisibility(visibilityDistance : Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mtr")
        textView_visibility.text = "Visibility : $visibilityDistance $unitAbbreviation"
    }

    private fun updateWind(windDirection : String, windSpeed : Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind : $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updatePrecipitation(precVolume : Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation : $precVolume $unitAbbreviation"
    }

    private fun updateCondition(condition : String) {
        textView_condition.text = condition
    }

    private fun updateTemperatures(temperature : Double, feelsLike : Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("`C", "`F")
        textView_temperature.text = "$temperature $unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateLocation(location : String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

}