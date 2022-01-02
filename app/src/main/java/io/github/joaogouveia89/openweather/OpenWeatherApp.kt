package io.github.joaogouveia89.openweather

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.github.joaogouveia89.openweather.di.AppModule
import io.github.joaogouveia89.openweather.di.DaggerAppComponent
import javax.inject.Inject

class OpenWeatherApp: Application() ,HasAndroidInjector {
    @Inject
    lateinit var mInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = mInjector
}