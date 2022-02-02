package io.github.joaogouveia89.openweather.weather_data

import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.liveData
import io.github.joaogouveia89.openweather.DAYS_AGO_TO_UPDATE
import io.github.joaogouveia89.openweather.EARTH_RADIUS_KM
import io.github.joaogouveia89.openweather.KM_TO_RECALCULATE
import io.github.joaogouveia89.openweather.ktx.daysAgo
import io.github.joaogouveia89.openweather.ktx.getDouble
import io.github.joaogouveia89.openweather.weather_data.local.LAST_API_CALL_DATE
import io.github.joaogouveia89.openweather.weather_data.local.LATITUDE
import io.github.joaogouveia89.openweather.weather_data.local.LONGITUDE
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject
import kotlin.math.*

class WeatherDataRepository @Inject constructor(
    private val localProvider: WeatherDataProvider,
    private val remoteProvider: WeatherDataProvider,
    private val preferences: SharedPreferences
) {
    private var _weatherList = listOf<Weather>()

    suspend fun getWeatherList(location: Location, loadingFunction :() -> Unit) = liveData(Dispatchers.IO) {

        val dataRequestManager = WeatherDataRequestManager(
            Pair(location.latitude, location.longitude),
            getLastLatLon(),
            getLastApiCallDaysAgo() ?: DAYS_AGO_TO_UPDATE)

        loadingFunction.invoke()

        _weatherList = if(dataRequestManager.requiresFreshData()){
            getRemoteWeatherData(location.latitude, location.longitude)
        }else{
            getLocalWeatherData()
        }

        emit(_weatherList)
    }



    //http://saulmm.github.io/mastering-coordinator
    private suspend fun getLocalWeatherData() = localProvider.getWeatherList()

    //this function also updates the local data
    private suspend fun getRemoteWeatherData(latitude: Double, longitude: Double): List<Weather>{
        localProvider.clearData()
        val weatherList = remoteProvider.getWeatherList(latitude, longitude)
        if(weatherList.isNotEmpty()) {
            localProvider.insertWeather(weatherList)
            updateLastLatLon(latitude, longitude)
            updateLastApiCallDate(System.currentTimeMillis())
        }
        return weatherList
    }

    private fun updateLastApiCallDate(time: Long) =
        preferences.edit()
            .putLong(LAST_API_CALL_DATE, time)
            .commit()

    private fun getLastApiCallDaysAgo(): Int?{
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

    private fun getLastLatLon() = Pair(
        preferences.getDouble(LATITUDE),
        preferences.getDouble(LONGITUDE)
    )
}
