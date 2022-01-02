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

        // TODO instead of passing app context, initialize repository and location manager here
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = mInjector
}