package io.github.joaogouveia89.openweather.models

import io.github.joaogouveia89.openweather.weather_data.local.entities.WeatherRequest

sealed class Response {
    sealed class LocalDatabase: Response(){
        class Loading(): LocalDatabase()
        class Success(val weatherRequest: WeatherRequest): LocalDatabase()
    }
}