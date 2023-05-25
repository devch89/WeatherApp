package com.chow.weatherapp.data.repository.remote

import com.chow.weatherapp.data.model.weather.Weather
import com.chow.weatherapp.data.remote.geocoder.GeoCodeApi
import com.chow.weatherapp.data.remote.weather.WeatherApi
import com.chow.weatherapp.util.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val geoCodeApi: GeoCodeApi,
    private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {

    override fun getForecast(
        city: String,
        state: String?,
        country: String?
    ): Flow<UIState<Weather>> = flow {
        emit(UIState.Loading)
        try {
            val response = weatherApi.getForecastByCity(city)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.Success(it))
                } ?: throw Exception("City unavailable")
            } else {
                throw Exception(response.errorBody()?.string())
            }
        } catch (e: java.lang.Exception) {
            emit(UIState.Error(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun getCityName(lat: Double, lon: Double): String {
        return geoCodeApi.getNameFromLocationCoordinates(lat, lon)
            .body()?.firstOrNull()?.name ?: ""
    }

}
