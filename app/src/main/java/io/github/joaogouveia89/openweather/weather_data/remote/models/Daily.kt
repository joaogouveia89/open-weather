package io.github.joaogouveia89.openweather.weather_data.remote.models

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("dt") val dt : Int,
    @SerializedName("sunrise") val sunrise : Int,
    @SerializedName("sunset") val sunset : Int,
    @SerializedName("moonrise") val moonrise : Int,
    @SerializedName("moonset") val moonset : Int,
    @SerializedName("moon_phase") val moonPhase : Double,
    @SerializedName("temp") val temp : Temp,
    @SerializedName("feels_like") val feelsLike : FeelsLike,
    @SerializedName("pressure") val pressure : Int,
    @SerializedName("humidity") val humidity : Int,
    @SerializedName("dew_point") val dewPoint : Double,
    @SerializedName("wind_speed") val windSpeed : Double,
    @SerializedName("wind_deg") val windDeg : Int,
    @SerializedName("wind_gust") val windGust : Double,
    @SerializedName("weather") val weather : List<Weather>,
    @SerializedName("clouds") val clouds : Int,
    @SerializedName("pop") val pop : Double,
    @SerializedName("uvi") val uvi : Double
)
