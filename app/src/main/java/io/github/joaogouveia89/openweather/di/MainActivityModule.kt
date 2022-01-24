package io.github.joaogouveia89.openweather.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.joaogouveia89.openweather.MainActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity
}