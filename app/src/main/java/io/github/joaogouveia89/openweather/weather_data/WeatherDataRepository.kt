package io.github.joaogouveia89.openweather.weather_data

import androidx.lifecycle.liveData
import io.github.joaogouveia89.openweather.models.Response
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

    fun getCoordinatesRequest(lat: Double, long: Double) = liveData{
        //TODO get coordinates request here, if the api fetch was done in less than 1 day, use the local stored data, otherwise fetch for new data
        api.service.fetchWeatherData(lat, long).apply {
            enqueue(openWeatherApiCallback)
        }
        emit(Response.LocalDatabase.Loading())
        emit(Response.LocalDatabase.Success(room.getCoordinatesRequest(lat, long)))
    }
}
