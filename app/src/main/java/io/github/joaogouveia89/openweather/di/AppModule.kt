package io.github.joaogouveia89.openweather.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRepository
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
    fun provideWeatherRepository(): WeatherDataRepository = WeatherDataRepository(
        WeatherDatabaseInstance(context),
        OpenWeatherApi(),
        context.getSharedPreferences(PREFERENCES_FILE_NAME, 0)
    )

    @Singleton
    @Provides
    fun provideWeatherLocationManager(): WeatherLocationManager = WeatherLocationManager(context)
}