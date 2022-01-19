package io.github.joaogouveia89.openweather.weather_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.joaogouveia89.openweather.weather_data.local.daos.WeatherDao
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather

@Database(entities = [Weather::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}