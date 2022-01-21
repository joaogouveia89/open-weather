package io.github.joaogouveia89.openweather.weather_data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    suspend fun getWeatherList(): List<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weather: List<Weather>)
}