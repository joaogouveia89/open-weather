package io.github.joaogouveia89.openweather.weather_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import io.github.joaogouveia89.openweather.ktx.daysAgo
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import io.github.joaogouveia89.openweather.weather_data.local.WeatherDatabaseInstance
import io.github.joaogouveia89.openweather.weather_data.remote.OpenWeatherApi
import io.github.joaogouveia89.openweather.weather_data.remote.models.OpenWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val api: OpenWeatherApi,
    private val room: WeatherDatabaseInstance
) {

    private val DAYS_AGO_TO_UPDATE = 1 // TODO MOVE THIS TO A SETTINGS FILE

    suspend fun getCoordinatesNext7DaysWeather(lat: Double, long: Double) = liveData(Dispatchers.IO){
        withContext(Dispatchers.IO){
            var weatherList = room.getWeather(lat, long)
            //TODO: Refactor, Date(weather[0].requestDate is assuming that all the db registers got the same requestDate
           if(weatherList.isEmpty() || Date(weatherList[0].requestDate).daysAgo() >= DAYS_AGO_TO_UPDATE){
               val response = api.service.fetchWeatherData(lat, long)
               weatherList = Weather.fromOpenWeatherResponse(response, lat, long)
               room.insertAll(weatherList)
            }
            emit(weatherList)
        }
    }
}
