package io.github.joaogouveia89.openweather

import android.location.Location
import io.github.joaogouveia89.openweather.weather_data.WeatherDataRequestManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherDataRequestManagerTest {
    @Mock
    private lateinit var lisbon: Location
    @Mock
    private lateinit var oeiras: Location
    @Mock
    private lateinit var rioDeJaneiro: Location

    private lateinit var dataRequestManager: WeatherDataRequestManager

    @Before
    fun setup(){
        Mockito.`when`(lisbon.latitude).thenReturn(38.7318067)
        Mockito.`when`(lisbon.longitude).thenReturn(-9.1440471)

        Mockito.`when`(oeiras.latitude).thenReturn(38.6985722)
        Mockito.`when`(oeiras.longitude).thenReturn(-9.3052697)

        Mockito.`when`(rioDeJaneiro.latitude).thenReturn(-22.908333)
        Mockito.`when`(rioDeJaneiro.longitude).thenReturn(-43.196388)
    }

    @Test
    fun currentLocationLisbon_lastLocationOeiras_lastApiCallZero() {
        dataRequestManager = WeatherDataRequestManager(
            Pair(lisbon.latitude, lisbon.longitude),
            Pair(oeiras.latitude, oeiras.longitude),
            0,
        )
        Assert.assertFalse(dataRequestManager.requiresFreshData())
    }

    @Test
    fun currentLocationOeiras_lastLocationLisbon_lastApiCallZero() {
        dataRequestManager = WeatherDataRequestManager(
            Pair(oeiras.latitude, oeiras.longitude),
            Pair(lisbon.latitude, lisbon.longitude),
            0,
        )
        Assert.assertFalse(dataRequestManager.requiresFreshData())
    }

    @Test
    fun currentLocationLisbon_lastLocationRiodeJaneiro_lastApiCallZero() {
        dataRequestManager = WeatherDataRequestManager(
            Pair(lisbon.latitude, lisbon.longitude),
            Pair(rioDeJaneiro.latitude, rioDeJaneiro.longitude),
            0,
        )
        Assert.assertTrue(dataRequestManager.requiresFreshData())
    }

    @Test
    fun currentLocationLisbon_lastLocationLisbon_lastApiCallZero() {
        dataRequestManager = WeatherDataRequestManager(
            Pair(lisbon.latitude, lisbon.longitude),
            Pair(lisbon.latitude, lisbon.longitude),
            0,
        )
        Assert.assertFalse(dataRequestManager.requiresFreshData())
    }

    @Test
    fun currentLocationLisbon_lastLocationLisbon_lastApiCallOne() {
        dataRequestManager = WeatherDataRequestManager(
            Pair(lisbon.latitude, lisbon.longitude),
            Pair(lisbon.latitude, lisbon.longitude),
            1,
        )
        Assert.assertTrue(dataRequestManager.requiresFreshData())
    }

    @Test
    fun currentLocationLisbon_lastLocationRiodeJaneiro_lastApiCallOne() {
        dataRequestManager = WeatherDataRequestManager(
            Pair(lisbon.latitude, lisbon.longitude),
            Pair(rioDeJaneiro.latitude, rioDeJaneiro.longitude),
            1,
        )
        Assert.assertTrue(dataRequestManager.requiresFreshData())
    }
}