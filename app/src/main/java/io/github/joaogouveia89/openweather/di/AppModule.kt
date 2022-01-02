package io.github.joaogouveia89.openweather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.joaogouveia89.openweather.weather_data.local.WeatherDatabase
import io.github.joaogouveia89.openweather.weather_data.remote.OpenWeatherApi
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Singleton
    @Provides
    fun provideWeatherDatabase(): WeatherDatabase = WeatherDatabase(context)

    @Singleton
    @Provides
    fun provideOpenWeatherApi():OpenWeatherApi = OpenWeatherApi(context)
}