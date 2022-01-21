package io.github.joaogouveia89.openweather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dagger.android.support.AndroidSupportInjection
import io.github.joaogouveia89.openweather.databinding.FragmentWeatherInformationBinding
import io.github.joaogouveia89.openweather.weather_data.local.entities.Weather
import timber.log.Timber
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WeatherInformationFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private var _binding: FragmentWeatherInformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val weatherListObserver = Observer<List<Weather>>{
        for(i in it){
            Timber.d("JOAODEBUG::$i")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherInformationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
        viewModel.requireUpdatedLocation()
        viewModel.weatherList.observe(viewLifecycleOwner, weatherListObserver)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}