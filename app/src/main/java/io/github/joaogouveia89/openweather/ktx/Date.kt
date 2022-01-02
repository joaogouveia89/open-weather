package io.github.joaogouveia89.openweather.ktx

import java.util.*

fun Date.daysAgo(): Int = ((Calendar.getInstance().time.time - time) / (1000 * 60 * 60 * 24)).toInt()