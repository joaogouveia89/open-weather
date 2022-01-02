package io.github.joaogouveia89.openweather.weather_data

sealed class WeatherFetchStatus {
    class Loading(): WeatherFetchStatus()
    class Success(): WeatherFetchStatus()
}