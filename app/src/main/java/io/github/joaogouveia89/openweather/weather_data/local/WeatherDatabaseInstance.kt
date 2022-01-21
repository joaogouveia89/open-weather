package io.github.joaogouveia89.openweather.weather_data.local

import android.content.Context
import androidx.room.Room
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import javax.inject.Inject

class WeatherDatabaseInstance @Inject constructor(private val context: Context) {

    private var db: WeatherDatabase = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    suspend fun getWeatherList(): List<Weather> =
        db.weatherDao().getWeatherList()

    suspend fun insertAll(weathers: List<Weather>) =
        db.weatherDao().insertAll(weathers)

    fun clearData() = db.clearAllTables()
}