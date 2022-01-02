package io.github.joaogouveia89.openweather.weather_data.local

import android.content.Context
import android.util.Log
import androidx.room.Room
import io.github.joaogouveia89.openweather.weather_data.local.entities.WeatherRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class WeatherDatabaseInstance @Inject constructor(val context: Context) {
    private var db: WeatherDatabase? = null

    fun initializeDatabase(){
        db = Room.databaseBuilder(
            context,
            WeatherDatabase::class.java, DATABASE_NAME
        ).build()
    }

    suspend fun getCoordinatesRequest(latitude: Double, longitude: Double): WeatherRequest = withContext(Dispatchers.IO) {
        var coordinatesRequest = db?.weatherRequestDao()?.getCoordinatesRequest(latitude, longitude)
        if(coordinatesRequest == null){
            coordinatesRequest = WeatherRequest(latitude = latitude, longitude = longitude, requestDate = Calendar.getInstance().time)
            db?.weatherRequestDao()?.insert(coordinatesRequest)
        }else{
            coordinatesRequest.requestDate = Calendar.getInstance().time
            db?.weatherRequestDao()?.updateCoordinatesRequest(coordinatesRequest)
        }
        coordinatesRequest
    }
}