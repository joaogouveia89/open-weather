package io.github.joaogouveia89.openweather.weather_information

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.AndroidSupportInjection
import io.github.joaogouveia89.openweather.MainViewModel
import io.github.joaogouveia89.openweather.R
import io.github.joaogouveia89.openweather.databinding.FragmentWeatherInformationBinding
import io.github.joaogouveia89.openweather.weather_data.WeatherCondition
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import io.github.joaogouveia89.openweather.weather_information.weather_list.WeatherListAdapter
import java.util.ArrayList
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

const val CITY_NAME = "CITY_NAME"
const val WEATHER_LIST = "WEATHER_LIST"

class WeatherInformationFragment : Fragment() {

    private var _binding: FragmentWeatherInformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val weatherListAdapter = WeatherListAdapter()

    private var cityName: String = ""
    private var weatherList = listOf<Weather>()

    private fun fillTodayData(it: Weather) {
        val weatherIcon = view?.findViewById<ImageView>(R.id.iv_weather_icon)
        val container = view?.findViewById<AppBarLayout>(R.id.main_appbar)
        val cityNameView = view?.findViewById<TextView>(R.id.tv_city)
        val tempMin = view?.findViewById<TextView>(R.id.tv_temp_min)
        val tempMax = view?.findViewById<TextView>(R.id.tv_temp_max)
        val clouds = view?.findViewById<TextView>(R.id.tv_clouds)
        val humidity = view?.findViewById<TextView>(R.id.tv_humidity)
        val windSpeed = view?.findViewById<TextView>(R.id.tv_wind_speed)
        val condition = WeatherCondition(it.openWeatherId)

        container?.setBackgroundResource(condition.background)
        weatherIcon?.setImageResource(condition.mainImage)
        cityNameView?.text = cityName
        tempMin?.text = getString(R.string.temperature_val, it.minTemp)
        tempMax?.text = getString(R.string.temperature_val, it.maxTemp)
        clouds?.text = getString(R.string.percent_val, it.clouds)
        humidity?.text = getString(R.string.percent_val, it.humidity)
        windSpeed?.text = getString(R.string.wind_speed_val, it.windSpeed)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherInformationBinding.inflate(inflater, container, false)
        cityName = arguments?.getString(CITY_NAME) ?: ""
        weatherList = arguments?.getParcelableArrayList(WEATHER_LIST) ?: emptyList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherListView = view.findViewById<RecyclerView>(R.id.weather_list)
        weatherListView.adapter = weatherListAdapter

        fillAllViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    companion object{
        fun newInstance(weatherList: List<Weather>, cityName: String): WeatherInformationFragment{
            val fragment = WeatherInformationFragment()
            val weatherAl = mutableListOf<Weather>()
            weatherAl.addAll(weatherList)

            val args = Bundle().apply {
                putString(CITY_NAME, cityName)
                putParcelableArrayList(WEATHER_LIST, weatherAl as ArrayList<Weather>)
            }
            fragment.arguments = args
            return fragment
        }
    }
}