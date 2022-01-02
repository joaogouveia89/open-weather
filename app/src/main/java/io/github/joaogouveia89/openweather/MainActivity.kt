package io.github.joaogouveia89.openweather

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import io.github.joaogouveia89.openweather.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //TODO Supressing permissions asking for now, since the first tests will be on API 19
    @SuppressLint("MissingPermission")
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val gc = Geocoder(this, Locale.getDefault())

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            if (hasGps) {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                Log.i("JOAODEBUG::", "GPS Location: ${location?.latitude} ${location?.longitude}")
                location?.let {
                    val addresses = gc.getFromLocation(it.latitude, it.longitude, 1)
                    Log.i("JOAODEBUG", addresses[0].getAddressLine(0))
                }

            }
            if (hasNetwork) {
                val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                Log.i("JOAODEBUG::", "Network Location: ${location?.latitude} ${location?.longitude}")
            }
        }

        return super.onCreateView(name, context, attrs)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}