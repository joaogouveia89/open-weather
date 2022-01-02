package io.github.joaogouveia89.openweather

import android.location.Location
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.github.joaogouveia89.openweather.models.Response
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import io.github.joaogouveia89.openweather.weather_data.local.entities.WeatherRequest
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherDataRepository,
    private val weatherLocationManager: WeatherLocationManager): ViewModel() {

    private val weatherRequestObserver = Observer<Response.LocalDatabase> {
        if(it is Response.LocalDatabase.Success) {
            val cal = Calendar.getInstance()
            cal.time = it.weatherRequest.requestDate
            Timber.i("JOAODEBUG::latitude = " + cal.get(Calendar.YEAR))
        }
    }

    fun initializeAllDependencies(){
        weatherLocationManager.requestLocation()
        weatherRepository.initialize()
        weatherLocationManager.currentLocation.observeForever(locationObserver)
    }

    private val locationObserver = Observer<Location>{
        weatherRepository.addUpdateWeatherRequest(it.latitude, it.longitude).observeForever(weatherRequestObserver)
    }

    override fun onCleared() {
        super.onCleared()
        weatherLocationManager.currentLocation.removeObserver(locationObserver)
    }
}