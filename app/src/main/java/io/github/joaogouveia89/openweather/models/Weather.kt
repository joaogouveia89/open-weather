package io.github.joaogouveia89.openweather.models

import java.util.Date

data class Weather(
    val date: Date,
    val temperatureCelsius: Double,
    val feelsLikeCelsius: Double,
    val clouds: Double,
    val humidity: Double,
    val windSpeed: Double,
    val icon: String,
    val openWeatherId: Int,
)
