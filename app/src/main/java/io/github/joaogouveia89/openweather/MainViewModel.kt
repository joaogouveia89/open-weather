package io.github.joaogouveia89.openweather

import android.location.Location
import androidx.lifecycle.*
import io.github.joaogouveia89.openweather.ktx.observeOnceVm
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.*

class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherDataRepository,
    private val weatherLocationManager: WeatherLocationManager): ViewModel() {

    val weatherList: LiveData<List<Weather>>
        get() = _weatherList

    val todayWeather: LiveData<Weather>
        get() = _todayWeather

    val cityName: String
        get() = weatherLocationManager.cityName

    private val _weatherList = MutableLiveData<List<Weather>>()
    private val _todayWeather = MutableLiveData<Weather>()

    private val currentLocationObserver = Observer<Location>{ currentLocation ->
        viewModelScope.launch {
            weatherRepository.getWeatherList(currentLocation).observeOnceVm(weatherListObserver)
        }
    }

    private val weatherListObserver = Observer<List<Weather>> {
        if(it == null || it.isEmpty()) return@Observer //TODO do something here to tell the view that it has an error during weather fetching
        _todayWeather.postValue(it[0])
        _weatherList.postValue(it.filterIndexed{ index, _ -> index != 0})
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