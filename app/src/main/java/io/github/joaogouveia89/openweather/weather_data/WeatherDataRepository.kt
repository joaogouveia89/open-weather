package io.github.joaogouveia89.openweather.weather_data

import androidx.lifecycle.liveData
import io.github.joaogouveia89.openweather.models.Response
import io.github.joaogouveia89.openweather.weather_data.local.WeatherDatabaseInstance
import io.github.joaogouveia89.openweather.weather_data.remote.OpenWeatherApi
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val api: OpenWeatherApi,
    private val room: WeatherDatabaseInstance
) {
    fun initialize(){
        room.initializeDatabase()
    }

    fun addUpdateWeatherRequest(lat: Double, long: Double) = liveData{
        emit(Response.LocalDatabase.Loading())

        emit(Response.LocalDatabase.Success(room.getCoordinatesRequest(lat, long)))
    }
}
