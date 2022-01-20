package io.github.joaogouveia89.openweather.weather_data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

class WeatherDatabaseInstance @Inject constructor(private val context: Context) {

    private var db: WeatherDatabase = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        DATABASE_NAME
    ).build()

    suspend fun getWeather(latitude: Double, longitude: Double): List<Weather> =
        db.weatherDao().getWeatherList(latitude, longitude)

    suspend fun insertAll(weathers: List<Weather>) =
        db.weatherDao().insertAll(weathers)
}