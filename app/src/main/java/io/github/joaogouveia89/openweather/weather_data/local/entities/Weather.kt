package io.github.joaogouveia89.openweather.weather_data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.joaogouveia89.openweather.ktx.toCelsius
import io.github.joaogouveia89.openweather.weather_data.remote.models.OpenWeatherResponse
import java.util.Date

@Entity
data class Weather(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "requestDate") val requestDate: Long,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "minTemp") val minTemp: Double,
    @ColumnInfo(name = "maxTemp") val maxTemp: Double,
    @ColumnInfo(name = "clouds") val clouds: Double,
    @ColumnInfo(name = "humidity") val humidity: Double,
    @ColumnInfo(name = "windSpeed") val windSpeed: Double,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "openWeatherId") val openWeatherId: Int,
){
    companion object{
        fun fromOpenWeatherResponse(response: OpenWeatherResponse) =
            response.daily.map {
                Weather(
                    requestDate = System.currentTimeMillis(),
                    latitude = response.lat,
                    longitude = response.lon,
                    date = it.dt,
                    minTemp = it.temp.min.toCelsius(),
                    maxTemp = it.temp.max.toCelsius(),
                    clouds = it.clouds.toDouble(),
                    humidity = it.humidity.toDouble(),
                    windSpeed = it.windSpeed,
                    icon = it.weather[0].icon,
                    openWeatherId = it.weather[0].id
                )
            }
    }
}
