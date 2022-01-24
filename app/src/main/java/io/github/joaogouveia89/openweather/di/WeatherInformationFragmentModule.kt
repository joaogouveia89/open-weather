package io.github.joaogouveia89.openweather.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.joaogouveia89.openweather.weather_information.WeatherInformationFragment

@Module
abstract class WeatherInformationFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherInformationFragmentInjector(): WeatherInformationFragment
}