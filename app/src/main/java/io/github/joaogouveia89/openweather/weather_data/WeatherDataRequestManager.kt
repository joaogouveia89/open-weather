package io.github.joaogouveia89.openweather.weather_data

import io.github.joaogouveia89.openweather.DAYS_AGO_TO_UPDATE
import io.github.joaogouveia89.openweather.EARTH_RADIUS_KM
import io.github.joaogouveia89.openweather.KM_TO_RECALCULATE
import kotlin.math.sin
import kotlin.math.pow
import kotlin.math.cos
import kotlin.math.atan2
import kotlin.math.sqrt

class WeatherDataRequestManager(
    private val currentLocation: Pair<Double?, Double?>,
    private val lastLocation: Pair<Double?, Double?>,
    private val lastApiCallInDays: Int) {

    // thanks to https://www.movable-type.co.uk/scripts/latlong.html
    private fun geographicDistance
                (from: Pair<Double?, Double?>,
                 to: Pair<Double?, Double?>): Double? {

        val fromLat = from.first ?: return null
        val fromLong = from.second ?: return null
        val toLat = to.first ?: return null
        val toLong = to.second ?: return null

        val a = sin((toLat - fromLat) / 2).pow(2) + cos(toLat) * cos(fromLat) * sin((toLong - fromLong) / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))

        return EARTH_RADIUS_KM * c
    }

    fun requiresFreshData(): Boolean{
        val distance = geographicDistance(
            currentLocation,
            lastLocation
        ) ?: KM_TO_RECALCULATE

        return distance >= KM_TO_RECALCULATE || lastApiCallInDays >= DAYS_AGO_TO_UPDATE
    }

}