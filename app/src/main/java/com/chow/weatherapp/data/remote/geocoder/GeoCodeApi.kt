package com.chow.weatherapp.data.remote.geocoder

import com.chow.weatherapp.data.model.geocode.GeocodeResponse
import com.chow.weatherapp.util.GeoCodeUtil
import com.chow.weatherapp.util.GeoCodeUtil.GEOCODE_DIRECT
import com.chow.weatherapp.util.GeoCodeUtil.GEOCODE_REVERSE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeApi {

    @GET(GEOCODE_REVERSE)
    suspend fun getNameFromLocationCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String = GeoCodeUtil.API_KEY
    ): Response<GeocodeResponse>

    @GET(GEOCODE_DIRECT)
    suspend fun getLocationCoordinatesFromName(
        @Query("q") name: String,
        @Query("limit") limit: Int = 10,
        @Query("appid") apiKey: String = GeoCodeUtil.API_KEY
    ): Response<GeocodeResponse>
}