package com.chow.weatherapp.data.repository.remote

import com.chow.weatherapp.data.model.weather.Weather
import com.chow.weatherapp.util.UIState
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    fun getForecast(
        city: String,
        state: String? = null,
        country: String? = null
    ): Flow<UIState<Weather>>

    suspend fun getCityName(lat: Double, lon: Double): String

}

