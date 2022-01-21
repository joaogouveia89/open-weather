package io.github.joaogouveia89.openweather.ktx

import android.content.SharedPreferences

fun SharedPreferences.getDouble(key: String) = this.getString(key, null)?.toDouble()