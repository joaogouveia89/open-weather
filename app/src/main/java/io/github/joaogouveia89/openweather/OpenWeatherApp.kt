package io.github.joaogouveia89.openweather

import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.github.joaogouveia89.openweather.di.AppModule
import io.github.joaogouveia89.openweather.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject


class OpenWeatherApp: MultiDexApplication() ,HasAndroidInjector {
    @Inject
    lateinit var mInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
            .inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = mInjector
}