package io.github.joaogouveia89.openweather.weather_data

import androidx.lifecycle.liveData
import io.github.joaogouveia89.openweather.ktx.daysAgo
import io.github.joaogouveia89.openweather.models.Weather
import io.github.joaogouveia89.openweather.weather_data.local.WeatherDatabaseInstance
import io.github.joaogouveia89.openweather.weather_data.remote.OpenWeatherApi
import io.github.joaogouveia89.openweather.weather_data.remote.models.OpenWeatherResponse
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber
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

    suspend fun getCoordinatesNext7DaysWeather(lat: Double, long: Double) = liveData<List<Weather>>{
        //TODO get coordinates request here, if the api fetch was done in less than 1 day, use the local stored data, otherwise fetch for new data
       val lastRequest = room.getCoordinatesRequest(lat, long)
        if(lastRequest != null && lastRequest.requestDate.daysAgo() >= DAYS_AGO_TO_UPDATE){
            api.service.fetchWeatherData(lat, long).apply {
                enqueue(openWeatherApiCallback)
            }
        }else{
            // TODO return from room all the data
        }
    }
}
