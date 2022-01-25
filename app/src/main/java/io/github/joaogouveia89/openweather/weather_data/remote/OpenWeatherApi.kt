package io.github.joaogouveia89.openweather.weather_data.remote

import io.github.joaogouveia89.openweather.ktx.getUnsafeOkHttpClient
import io.github.joaogouveia89.openweather.weather_data.WeatherDataProvider
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OpenWeatherApi: WeatherDataProvider() {
    private val logging =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    private var client = getUnsafeOkHttpClient()
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val service: OpenWeather
        @Synchronized get

    init {
        service = retrofit.create(OpenWeather::class.java)
    }

    override suspend fun getWeatherList(latitude: Double, longitude: Double): List<Weather> =
        Weather.fromOpenWeatherResponse(service.fetchWeatherData(latitude, longitude))

}