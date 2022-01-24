package io.github.joaogouveia89.openweather.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import io.github.joaogouveia89.openweather.OpenWeatherApp
import javax.inject.Singleton

@Singleton
@Component(modules = [
        AndroidInjectionModule::class,
        MainActivityModule::class,
        AppModule::class
])
interface AppComponent {
    fun inject(openWeatherApp: OpenWeatherApp)
}