package io.github.joaogouveia89.openweather

import android.location.Location
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import javax.inject.Inject

class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherDataRepository,
    private val weatherLocationManager: WeatherLocationManager): ViewModel() {

    fun initializeLocationFetching(){
        weatherLocationManager.requestLocation()
        weatherLocationManager.currentLocation.observeForever(locationObserver)
    }

    private val locationObserver = Observer<Location>{
        Log.i("JOAODEBUG", "lat = ${it.latitude}")
    }

    override fun onCleared() {
        super.onCleared()
        weatherLocationManager.currentLocation.removeObserver(locationObserver)
    }
}