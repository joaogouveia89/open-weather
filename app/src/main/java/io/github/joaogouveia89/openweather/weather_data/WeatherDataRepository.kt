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

    private val openWeatherApiCallback = object: Callback<OpenWeatherResponse> {
        override fun onResponse(
            call: Call<OpenWeatherResponse>,
            response: retrofit2.Response<OpenWeatherResponse>
        ) {
            if(response.code() == 200){
                //TODO Action of api fetching ok
            }
        }

        override fun onFailure(call: Call<OpenWeatherResponse>, t: Throwable) {
            //TODO Action of api fetching error
            Timber.e("ERROR:${t.message}")
        }

    }

    suspend fun getCoordinatesNext7DaysWeather(lat: Double, long: Double) = liveData(Dispatchers.IO){
        withContext(Dispatchers.IO){
            var weatherList = room.getWeather(lat, long)
            //TODO: Refactor, Date(weather[0].requestDate is assuming that all the db registers got the same requestDate
           if(weatherList.isEmpty() || Date(weatherList[0].requestDate).daysAgo() >= DAYS_AGO_TO_UPDATE){
               Timber.d("JOAODEBUG::CALLING API, wl size = ${weatherList.size}")
                val response = api.service.fetchWeatherData(lat, long)
                weatherList = Weather.fromOpenWeatherResponse(response)
               room.insertAll(weatherList)
            }else{
               Timber.d("JOAODEBUG::FROM ROOM")
           }
            emit(weatherList)
        }//TODO get coordinates request here, if the api fetch was done in less than 1 day, use the local stored data, otherwise fetch for new data
    }
}
