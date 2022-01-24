package io.github.joaogouveia89.openweather.weather_information.weather_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.joaogouveia89.openweather.R
import io.github.joaogouveia89.openweather.weather_data.WeatherCondition
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import java.util.*

class WeatherListItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    private val dayOfWeek: TextView = view.findViewById(R.id.tv_day_of_week)
    private val date: TextView = view.findViewById(R.id.tv_date)
    private val weatherIcon: ImageView = view.findViewById(R.id.iv_weather_icon_list)
    private val weatherCharacteristic: TextView = view.findViewById(R.id.tv_weather_characteristic)
    private val minTemp: TextView = view.findViewById(R.id.tv_list_temp_min)
    private val maxTemp: TextView = view.findViewById(R.id.tv_list_temp_max)
    private val windSpeed: TextView = view.findViewById(R.id.tv_list_wind_speed)
    private val humidity: TextView = view.findViewById(R.id.tv_list_humidity)

    var weather: Weather? = null
        set(value) {
            if (value != null){
                field = value
                val calendar = GregorianCalendar()
                calendar.timeInMillis = value.date * 1000
                dayOfWeek.text = view.resources.getStringArray(R.array.days_of_week)[calendar.get(Calendar.DAY_OF_WEEK) - 1]
                date.text = view.resources.getString(R.string.date, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1)
                weatherIcon.setImageResource(WeatherCondition(value.openWeatherId).mainImage)
                weatherCharacteristic.text = value.weatherCharacteristic
                minTemp.text = view.resources.getString(R.string.temperature_val, value.minTemp)
                maxTemp.text = view.resources.getString(R.string.temperature_val, value.maxTemp)
                windSpeed.text = view.resources.getString(R.string.wind_speed_val, value.windSpeed)
                humidity.text = view.resources.getString(R.string.percent_val, value.humidity)
            }
        }
}