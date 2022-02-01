package io.github.joaogouveia89.openweather.weather_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import io.github.joaogouveia89.openweather.R
import io.github.joaogouveia89.openweather.weather_data.WeatherCondition
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import io.github.joaogouveia89.openweather.weather_information.weather_list.WeatherListAdapter

class WeatherInformationFragment : Fragment() {

    private var weatherListView: RecyclerView? = null
    private var container: AppBarLayout? = null
    private var weatherIcon: ImageView? = null
    private var toolbar: Toolbar? = null
    private var tempMin: TextView? = null
    private var tempMax: TextView? = null
    private var clouds: TextView? = null
    private var humidity: TextView? = null
    private var windSpeed: TextView? = null

    private val weatherListAdapter = WeatherListAdapter()

    private var cityName: String = ""
    private var weatherList = listOf<Weather>()

    private fun fillTodayData(weather: Weather) {
        val condition = WeatherCondition(weather.openWeatherId)
        container?.setBackgroundResource(condition.background)
        weatherIcon?.setImageResource(condition.mainImage)
        toolbar?.title = cityName
        tempMin?.text = getString(R.string.temperature_val, weather.minTemp)
        tempMax?.text = getString(R.string.temperature_val, weather.maxTemp)
        clouds?.text = getString(R.string.percent_val, weather.clouds)
        humidity?.text = getString(R.string.percent_val, weather.humidity)
        windSpeed?.text = getString(R.string.wind_speed_val, weather.windSpeed)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_weather_information, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        weatherListView?.adapter = weatherListAdapter

        fillAllViews()
    }

    private fun initViews(view: View) {
        weatherListView = view.findViewById(R.id.weather_list)
        container = view.findViewById(R.id.main_appbar)
        weatherIcon = view.findViewById(R.id.iv_weather_icon)
        toolbar = view.findViewById(R.id.mainToolbar)
        tempMin = view.findViewById(R.id.tv_temp_min)
        tempMax = view.findViewById(R.id.tv_temp_max)
        clouds = view.findViewById(R.id.tv_clouds)
        humidity = view.findViewById(R.id.tv_humidity)
        windSpeed = view.findViewById(R.id.tv_wind_speed)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        weatherListView = null
        container = null
        weatherIcon = null
        toolbar = null
        tempMin = null
        tempMax = null
        clouds = null
        humidity = null
        windSpeed = null
    }

    fun updateData(weatherList: List<Weather>, cityName: String){
        this.cityName = cityName
        this.weatherList = weatherList
        fillAllViews()
    }

    private fun fillAllViews() {
        if(weatherList.isEmpty()) return
        fillTodayData(weatherList[0])
        weatherListAdapter.submitList(weatherList.filterIndexed { index, _ -> index != 0 })
    }
}