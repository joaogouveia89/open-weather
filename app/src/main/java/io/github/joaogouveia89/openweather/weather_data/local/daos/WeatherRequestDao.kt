package io.github.joaogouveia89.openweather.weather_data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.joaogouveia89.openweather.weather_data.local.entities.WeatherRequest

@Dao
interface WeatherRequestDao {
    @Insert
    fun insert(weatherRequest: WeatherRequest)

    @Query("SELECT * FROM weatherRequest WHERE latitude=:latitude AND longitude=:longitude")
    fun getCoordinatesRequest(latitude: Double, longitude: Double): WeatherRequest?

    @Update
    fun updateCoordinatesRequest(weatherRequest: WeatherRequest)
}