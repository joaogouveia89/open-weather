package io.github.joaogouveia89.openweather.weather_data.remote

import io.github.joaogouveia89.openweather.BuildConfig
import io.github.joaogouveia89.openweather.weather_data.remote.models.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeather {
    @GET("/data/2.5/onecall?appid=${BuildConfig.OPEN_WEATHER_API_TOKEN}&exclude=minutely,hourly,alerts,current")
    suspend fun fetchWeatherData(@Query("lat") latitude: Double, @Query("lon") longitude: Double): OpenWeatherResponse
}