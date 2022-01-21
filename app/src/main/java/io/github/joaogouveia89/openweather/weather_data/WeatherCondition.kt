package io.github.joaogouveia89.openweather.weather_data

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import io.github.joaogouveia89.openweather.R

class WeatherCondition(val id: Int) {
    val mainImage: Int
        get() = _mainImage

    val background: Int
        get() = _background

    @DrawableRes private var _mainImage: Int = 0
    @ColorRes private var _background: Int = 0

    // reference https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2
    // icons https://www.svgrepo.com/vectors/thunderstorm/
    init {
        when(id){
            800 ->{
                _mainImage = R.drawable.ic_sun
                _background = R.color.clear_sky
            }
            in 500..531 ->{
                _mainImage = R.drawable.ic_rain
                _background = R.color.rainy_sky
            }
            in 200..232 ->{
                _mainImage = R.drawable.ic_rain
                _background = R.color.rainy_sky
            }
        }
    }
}
