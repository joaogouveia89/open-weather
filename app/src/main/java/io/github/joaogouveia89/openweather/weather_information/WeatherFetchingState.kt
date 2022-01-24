package io.github.joaogouveia89.openweather.weather_information

sealed class WeatherFetchingState{
    object Loading : WeatherFetchingState()
    object Success : WeatherFetchingState()
}