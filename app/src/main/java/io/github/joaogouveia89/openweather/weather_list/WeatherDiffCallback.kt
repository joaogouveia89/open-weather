package io.github.joaogouveia89.openweather.weather_list

import androidx.recyclerview.widget.DiffUtil
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather

object WeatherDiffCallback : DiffUtil.ItemCallback<Weather>(){
    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean =
        oldItem.uid == newItem.uid
}