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
    suspend fun getWeatherList(location: Location) = liveData(Dispatchers.IO) {
        val lastCoordinates = getLastLatLon()

        val distance = geographicDistance(
            Pair(location.latitude, location.longitude),
            lastCoordinates
        )

        val lastUpdateDaysAgo = getLastApiCallDaysAgo()

        if(lastCoordinates.first == null ||
            lastCoordinates.second == null ||
            (distance != null && distance > KM_TO_RECALCULATE) ||
            lastUpdateDaysAgo == null ||
            lastUpdateDaysAgo >= DAYS_AGO_TO_UPDATE
        ){
            emit(getRemoteWeatherData(location.latitude, location.longitude))
        }else{
            emit(getLocalWeatherData())
        }
    }

    // thanks to https://www.movable-type.co.uk/scripts/latlong.html
    private fun geographicDistance
                (from: Pair<Double, Double>,
                 to: Pair<Double?, Double?>): Double? {
        if(to.first == null || to.second == null) return null
        // just to avoid compiling error
        val firstLat = to.first ?: 0.0
        val firstLon = to.second ?: 0.0

        val a = sin((firstLat - from.first) / 2).pow(2) + cos(firstLat) * cos(from.first) * sin((firstLon - from.second) / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))

        return EARTH_RADIUS_KM * c
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
