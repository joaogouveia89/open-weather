package io.github.joaogouveia89.openweather.weather_data.local

import android.content.Context
import androidx.lifecycle.liveData
import androidx.room.Room
import io.github.joaogouveia89.openweather.weather_data.local.entities.WeatherRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherDatabaseInstance @Inject constructor(private val context: Context) {
    private var db: WeatherDatabase? = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java, DATABASE_NAME
    ).build()

    fun getCoordinatesRequest(latitude: Double, longitude: Double): WeatherRequest? =
        db?.weatherRequestDao()?.getCoordinatesRequest(latitude, longitude)
}