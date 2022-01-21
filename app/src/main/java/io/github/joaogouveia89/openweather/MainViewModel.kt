package io.github.joaogouveia89.openweather

import android.location.Location
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.joaogouveia89.openweather.ktx.observeOnceVm
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.*

class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherDataRepository,
    private val weatherLocationManager: WeatherLocationManager): ViewModel() {

    private val currentLocationObserver = Observer<Location>{ currentLocation ->
        viewModelScope.launch {
            val lastCoordinates = weatherRepository.getLastLatLon()

            val distance = geographicDistance(
                Pair(currentLocation.latitude, currentLocation.longitude),
                lastCoordinates
            )

            val lastUpdateDaysAgo = weatherRepository.getLastApiCallDaysAgo()

            // if there is no locations already saved
            if(lastCoordinates.first == null ||
                lastCoordinates.second == null ||
                (distance != null && distance > KM_TO_RECALCULATE) ||
                lastUpdateDaysAgo == null ||
                lastUpdateDaysAgo >= DAYS_AGO_TO_UPDATE){
                weatherRepository
                    .getRemoteWeatherData(
                        currentLocation.latitude,
                        currentLocation.longitude
                    ).observeOnceVm(weatherListObserver)
            }else{
                weatherRepository
                    .getLocalWeatherData()
                    .observeOnceVm(weatherListObserver)
            }
        }
    }

    // thanks to https://www.movable-type.co.uk/scripts/latlong.html
    private fun geographicDistance
                (from: Pair<Double, Double>,
                 to: Pair<Double?, Double?>): Double? {
        if(to.first == null || to.second == null) return null
        // just to avoid compiling error
        val firstLat = to.first ?: 0.0
        val firstLon = to.second ?: 0.0

        val a = sin((firstLat - from.first) / 2).pow(2) + cos(firstLat) * cos(from.first) * sin((firstLon - from.second) / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))

        return EARTH_RADIUS_KM * c
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