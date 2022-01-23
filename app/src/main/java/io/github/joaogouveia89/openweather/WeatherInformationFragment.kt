package io.github.joaogouveia89.openweather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.AndroidSupportInjection
import io.github.joaogouveia89.openweather.databinding.FragmentWeatherInformationBinding
import io.github.joaogouveia89.openweather.weather_data.WeatherCondition
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherInformationFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private var _binding: FragmentWeatherInformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val weatherListObserver = Observer<List<Weather>>{

    }

    private val todayWeatherObserver = Observer<Weather>{
        val weatherIcon = view?.findViewById<ImageView>(R.id.iv_weather_icon)
        val container = view?.findViewById<AppBarLayout>(R.id.main_appbar)
        val cityName = view?.findViewById<TextView>(R.id.tv_city)
        val tempMin = view?.findViewById<TextView>(R.id.tv_temp_min)
        val tempMax = view?.findViewById<TextView>(R.id.tv_temp_max)
        val clouds = view?.findViewById<TextView>(R.id.tv_clouds)
        val humidity = view?.findViewById<TextView>(R.id.tv_humidity)
        val windSpeed = view?.findViewById<TextView>(R.id.tv_wind_speed)

        val condition = WeatherCondition(it.openWeatherId)
        container?.setBackgroundResource(condition.background)
        weatherIcon?.setImageResource(condition.mainImage)
        cityName?.text = viewModel.cityName
        tempMin?.text = getString(R.string.temperature_val, it.minTemp)
        tempMax?.text = getString(R.string.temperature_val, it.maxTemp)
        clouds?.text = getString(R.string.percent_val, it.clouds)
        humidity?.text = getString(R.string.percent_val, it.humidity)
        windSpeed?.text = getString(R.string.wind_speed_val, it.windSpeed)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherInformationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
        viewModel.requireUpdatedLocation()
        viewModel.weatherList.observe(viewLifecycleOwner, weatherListObserver)
        viewModel.todayWeather.observe(viewLifecycleOwner, todayWeatherObserver)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}