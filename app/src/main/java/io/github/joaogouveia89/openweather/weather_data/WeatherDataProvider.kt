package io.github.joaogouveia89.openweather.weather_data

import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather

abstract class WeatherDataProvider {
    open suspend fun insertWeather(weathers: List<Weather>): Unit? = null
    open fun clearData(): Unit? = null

    abstract suspend fun getWeatherList(latitude: Double = 0.0, longitude: Double = 0.0): List<Weather>
}