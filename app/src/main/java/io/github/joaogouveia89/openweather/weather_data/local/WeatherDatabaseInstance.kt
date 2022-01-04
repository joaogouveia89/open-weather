package io.github.joaogouveia89.openweather.weather_data.local

import android.content.Context
import androidx.room.Room
import io.github.joaogouveia89.openweather.weather_data.local.entities.WeatherRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherDatabaseInstance @Inject constructor(val context: Context) {
    private var db: WeatherDatabase? = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java, DATABASE_NAME
    ).build()

    suspend fun getCoordinatesRequest(latitude: Double, longitude: Double): WeatherRequest? = withContext(Dispatchers.IO) {
       db?.weatherRequestDao()?.getCoordinatesRequest(latitude, longitude)
    }
}