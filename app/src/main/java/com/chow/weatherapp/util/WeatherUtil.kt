package com.chow.weatherapp.util

import kotlin.math.roundToInt


object WeatherUtil {
    const val LOCATION_KEY = "LOCATION_KEY"
    const val BASE_URL_FORECAST = "https://api.openweathermap.org"
    const val WEATHER_UNIT = "imperial"
    const val WEATHER_PATH = "data/2.5/weather"
    const val IMAGE_ENDPOINT = "https://openweathermap.org/img/wn/"

    fun Double.roundOfToTwo(): Int {
        return (this * 100).roundToInt() / 100
    }
}