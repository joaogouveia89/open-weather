package io.github.joaogouveia89.openweather.weather_data.remote.models

import com.google.gson.annotations.SerializedName

data class OpenWeatherResponse(
    @SerializedName("lat") val lat : Double,
    @SerializedName("lon") val lon : Double,
    @SerializedName("timezone") val timezone : String,
    @SerializedName("timezone_offset") val timezoneOffset : Int,
    @SerializedName("daily") val daily : List<Daily>
)
