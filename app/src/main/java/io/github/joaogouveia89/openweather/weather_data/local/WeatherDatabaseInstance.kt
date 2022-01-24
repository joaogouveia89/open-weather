package io.github.joaogouveia89.openweather.weather_data.local

import android.content.Context
import androidx.room.Room
import io.github.joaogouveia89.openweather.weather_data.WeatherDataProvider
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import javax.inject.Inject

class WeatherDatabaseInstance @Inject constructor(private val context: Context):
    WeatherDataProvider() {

    private var db: WeatherDatabase = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    override suspend fun getWeatherList(latitude: Double, longitude: Double): List<Weather> =
        db.weatherDao().getWeatherList()

    override suspend fun insertWeather(weathers: List<Weather>) =
        db.weatherDao().insertAll(weathers)

    override fun clearData() = db.clearAllTables()
}