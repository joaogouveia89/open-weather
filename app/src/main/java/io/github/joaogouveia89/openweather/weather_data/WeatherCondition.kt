package io.github.joaogouveia89.openweather.weather_data

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
            800 -> {
                _mainImage = R.drawable.ic_sun
                _background = R.color.clear_sky
            }
            in 500..531 -> {
                _mainImage = R.drawable.ic_rain
                _background = R.color.foggy_sky
            }
            in 200..232 -> {
                _mainImage = R.drawable.ic_thunderstorm
                _background = R.color.foggy_sky
            }
            in 300..321 -> {
                _mainImage = R.drawable.ic_drizzle
                _background = R.color.foggy_sky
            }
            in 600..622 -> {
                _mainImage = R.drawable.ic_snow
                _background = R.color.foggy_sky
            }
            in 700..781 -> {
                _mainImage = R.drawable.ic_foggy
                _background = R.color.foggy_sky
            }
            in 801..804 -> {
                _mainImage = R.drawable.ic_foggy
                _background = R.color.clear_sky
            }
        }
    }
}
