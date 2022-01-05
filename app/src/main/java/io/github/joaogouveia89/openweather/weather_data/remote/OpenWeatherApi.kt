package io.github.joaogouveia89.openweather.weather_data.remote

import io.github.joaogouveia89.openweather.ktx.getUnsafeOkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class OpenWeatherApi {
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

    val service: OpenWeather
        @Synchronized get

    init {
        service = retrofit.create(OpenWeather::class.java)
    }
}