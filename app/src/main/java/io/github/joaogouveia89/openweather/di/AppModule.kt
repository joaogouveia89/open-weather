package io.github.joaogouveia89.openweather.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.github.joaogouveia89.openweather.weather_data.WeatherLocationManager
import io.github.joaogouveia89.openweather.weather_data.local.WeatherDatabaseInstance
import io.github.joaogouveia89.openweather.weather_data.remote.OpenWeatherApi
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Singleton
    @Provides
    fun provideWeatherDatabaseInstance(): WeatherDatabaseInstance = WeatherDatabaseInstance(context)

    @Singleton
    @Provides
    fun provideOpenWeatherApi():OpenWeatherApi = OpenWeatherApi()

    @Singleton
    @Provides
    fun provideWeatherLocationManager(): WeatherLocationManager = WeatherLocationManager(context)

    @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
}