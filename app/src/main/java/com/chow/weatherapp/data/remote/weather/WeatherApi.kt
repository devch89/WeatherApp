package com.chow.weatherapp.data.remote.weather


import com.chow.weatherapp.data.model.weather.Weather
import com.chow.weatherapp.util.GeoCodeUtil.API_KEY
import com.chow.weatherapp.util.WeatherUtil.WEATHER_PATH
import com.chow.weatherapp.util.WeatherUtil.WEATHER_UNIT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WEATHER_PATH)
    suspend fun getForecastByCity(
        @Query("q") name: String,
        @Query("units") units: String = WEATHER_UNIT,
        @Query("appid") appid: String = API_KEY
    ): Response<Weather>
}