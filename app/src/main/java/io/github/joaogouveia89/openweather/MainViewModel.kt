package io.github.joaogouveia89.openweather

import android.location.Location
import androidx.lifecycle.*
import io.github.joaogouveia89.openweather.ktx.observeOnceVm
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import io.github.joaogouveia89.openweather.weather_information.WeatherFetchingState
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.*

class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherDataRepository,
    private val weatherLocationManager: WeatherLocationManager): ViewModel() {

    val weatherList: List<Weather>
        get() = _weatherList

    val weatherFetchingState: LiveData<WeatherFetchingState>
        get() = _weatherFetchingState

    val cityName: String
        get() = weatherLocationManager.cityName

    private var _weatherFetchingState = MutableLiveData<WeatherFetchingState>()

    private var _weatherList = listOf<Weather>()

    private val currentLocationObserver = Observer<Location>{ currentLocation ->
        viewModelScope.launch {
            weatherRepository.getWeatherList(currentLocation) {
                _weatherFetchingState.postValue(WeatherFetchingState.Loading)
            }.observeOnceVm(weatherListObserver)
        }
    }

    private val weatherListObserver = Observer<List<Weather>> {
        if(it == null || it.isEmpty()) return@Observer //TODO do something here to tell the view that it has an error during weather fetching
        _weatherFetchingState.postValue(WeatherFetchingState.Success)
        _weatherList = it
    }

    fun requireUpdatedLocation(){
        weatherLocationManager.requestLocation()
        weatherLocationManager.currentLocation.observeForever(currentLocationObserver)
    }

    override fun onCleared() {
        super.onCleared()
        weatherLocationManager.currentLocation.removeObserver(currentLocationObserver)
    }
}