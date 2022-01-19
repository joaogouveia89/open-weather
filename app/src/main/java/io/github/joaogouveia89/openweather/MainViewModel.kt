package io.github.joaogouveia89.openweather

import android.location.Location
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.joaogouveia89.openweather.ktx.observeOnceVm
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherDataRepository,
    private val weatherLocationManager: WeatherLocationManager): ViewModel() {

    private val currentLocationObserver = Observer<Location>{ currentLocation ->
        viewModelScope.launch {
            weatherRepository
                .getCoordinatesNext7DaysWeather(currentLocation.latitude, currentLocation.longitude)
                .observeOnceVm(weatherListObserver)
        }
    }

    private val weatherListObserver = Observer<List<Weather>> {
        it.forEach {
            Timber.d("JOAODEBUG::$it")
        }
    }

    fun initializeAllDependencies(){
        weatherLocationManager.requestLocation()
        weatherLocationManager.currentLocation.observeForever(currentLocationObserver)
    }

    override fun onCleared() {
        super.onCleared()
        weatherLocationManager.currentLocation.removeObserver(currentLocationObserver)
    }
}