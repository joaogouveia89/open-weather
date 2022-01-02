package io.github.joaogouveia89.openweather

import androidx.lifecycle.ViewModel
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
import javax.inject.Inject

class MainViewModel
    @Inject constructor(
    weatherRepository: WeatherDataRepository): ViewModel() {
}