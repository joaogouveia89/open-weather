package io.github.joaogouveia89.openweather.weather_data

import io.github.joaogouveia89.openweather.weather_data.local.WeatherDatabase
import io.github.joaogouveia89.openweather.weather_data.remote.OpenWeatherApi
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val api: OpenWeatherApi,
    private val room: WeatherDatabase
) {

}
