package io.github.joaogouveia89.openweather

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.android.AndroidInjection
import io.github.joaogouveia89.openweather.databinding.ActivityMainBinding
import io.github.joaogouveia89.openweather.weather_information.WeatherFetchingState
import io.github.joaogouveia89.openweather.weather_information.WeatherInformationFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    private val weatherInformationFragment: WeatherInformationFragment = WeatherInformationFragment.newInstance(
        emptyList(), "")

    private val fetchingStateObserver = Observer<WeatherFetchingState>{ it ->

        val fragment = when(it){
            is WeatherFetchingState.Success -> weatherInformationFragment.apply {
                    updateData(viewModel.weatherList, viewModel.cityName)
            }
            WeatherFetchingState.Loading -> Fragment() // TODO Create the loading fragment
        }
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        viewModel.requireUpdatedLocation()
        viewModel.weatherFetchingState.observe(this, fetchingStateObserver)
        setContentView(binding.root)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}