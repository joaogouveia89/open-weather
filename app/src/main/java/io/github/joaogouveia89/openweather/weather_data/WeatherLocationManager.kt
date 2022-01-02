package io.github.joaogouveia89.openweather.weather_data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import javax.inject.Inject

class WeatherLocationManager @Inject constructor(context: Context) {

    private val _currentLocation = MutableLiveData<Location>()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val locationSuccessCallback = OnSuccessListener<Location>{
        _currentLocation.value  = it
    }

    val currentLocation: LiveData<Location>
        get() = _currentLocation

    //TODO Supressing permissions asking for now, since the first tests will be on API 19
    @SuppressLint("MissingPermission")
    fun requestLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener(locationSuccessCallback)
    }
}