package io.github.joaogouveia89.openweather.weather_data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*
import javax.inject.Inject

class WeatherLocationManager @Inject constructor(private val context: Context) {

    private val _currentLocation = MutableLiveData<Location>()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 20 * 1000
    }

    val cityName: String
        get() {
            _currentLocation.value?.let {
                val gcd = Geocoder(context, Locale.getDefault())
                val addresses = gcd.getFromLocation(it.latitude,it.longitude, 1)
                return addresses[0].subAdminArea ?: addresses[0].adminArea ?: ""
            }
            return ""
        }

    private val locationCallback = object: LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (location != null) {
                    _currentLocation.value = location
                }
            }
        }
    }

    val currentLocation: LiveData<Location>
        get() = _currentLocation

    //TODO Supressing permissions asking for now, since the first tests will be on API 19
    @SuppressLint("MissingPermission")
    fun requestLocation(){
        fusedLocationClient
            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }
}