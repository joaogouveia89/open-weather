package io.github.joaogouveia89.openweather

import android.location.Location
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.github.joaogouveia89.openweather.ktx.daysAgo
import io.github.joaogouveia89.openweather.models.Response
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherDataRepository,
    private val weatherLocationManager: WeatherLocationManager): ViewModel() {

    private val DAYS_AGO_TO_UPDATE = 1 // TODO MOVE THIS TO A SETTINGS FILE

    private val locationObserver = Observer<Location>{
        weatherRepository.getCoordinatesRequest(it.latitude, it.longitude).observeForever(weatherRequestObserver)
    }

    private val weatherRequestObserver = Observer<Response.LocalDatabase> { it ->
        if(it is Response.LocalDatabase.Success) {
            it.weatherRequest.let { weatherRequest ->
                if(weatherRequest == null || weatherRequest.requestDate.daysAgo() >= DAYS_AGO_TO_UPDATE){
                    // update in localdatabase the last update for this coordinates and call the api with the data
                }else{
                    // get the local stored data for this coordinates
                }
            }
        }
    }

    fun initializeAllDependencies(){
        weatherLocationManager.requestLocation()
        weatherRepository.initialize()
        weatherLocationManager.currentLocation.observeForever(locationObserver)
    }

    override fun onCleared() {
        super.onCleared()
        weatherLocationManager.currentLocation.removeObserver(locationObserver)
    }
}