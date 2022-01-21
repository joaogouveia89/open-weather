package io.github.joaogouveia89.openweather.weather_data

import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.liveData
import io.github.joaogouveia89.openweather.ktx.daysAgo
import io.github.joaogouveia89.openweather.ktx.getDouble
import io.github.joaogouveia89.openweather.weather_data.local.WeatherDatabaseInstance
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import io.github.joaogouveia89.openweather.weather_data.remote.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val api: OpenWeatherApi,
    private val room: WeatherDatabaseInstance,
    private val preferences: SharedPreferences
) {
    private val LATITUDE = "LATITUDE"
    private val LONGITUDE = "LONGITUDE"
    private val LAST_API_CALL_DATE = "LAST_API_CALL_DATE"

    //http://saulmm.github.io/mastering-coordinator

    suspend fun getLocalWeatherData() = liveData(Dispatchers.IO){
        withContext(Dispatchers.IO){
            emit(room.getWeatherList())
        }
    }

    //this function also updates the local data
    suspend fun getRemoteWeatherData(latitude: Double, longitude: Double) = liveData(Dispatchers.IO){
        withContext(Dispatchers.IO){
                room.clearData()
                val response = api.service.fetchWeatherData(latitude, longitude)
                val weatherList = Weather.fromOpenWeatherResponse(response)
                room.insertAll(weatherList)
                updateLastLatLon(latitude, longitude)
                updateLastApiCallDate(System.currentTimeMillis())
                emit(weatherList)
        }
    }

    private fun updateLastApiCallDate(time: Long) =
        preferences.edit()
            .putLong(LAST_API_CALL_DATE, time)
            .commit()

    fun getLastApiCallDaysAgo(): Int?{
        preferences.getLong(LAST_API_CALL_DATE, -1).let {
            if (it > 0){
                return Date(it).daysAgo()
            }
        }

        return null
    }

    private fun updateLastLatLon(latitude: Double, longitude: Double) =
        preferences.edit().let {
            it.putString(LATITUDE, latitude.toString())
            it.putString(LONGITUDE, longitude.toString())
        }.commit()

    fun getLastLatLon() = Pair(
        preferences.getDouble(LATITUDE),
        preferences.getDouble(LONGITUDE)
    )
}
