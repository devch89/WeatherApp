package com.chow.weatherapp.data.model.weather


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon")
    val lon: Double? = null,
    @SerializedName("lat")
    val lat: Double? = null
)