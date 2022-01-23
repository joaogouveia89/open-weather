package io.github.joaogouveia89.openweather.weather_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.github.joaogouveia89.openweather.R
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather

class WeatherListAdapter: ListAdapter<Weather, WeatherListItemViewHolder>(WeatherDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListItemViewHolder {
        return WeatherListItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherListItemViewHolder, position: Int) {
        holder.weather = currentList[position]
    }
}