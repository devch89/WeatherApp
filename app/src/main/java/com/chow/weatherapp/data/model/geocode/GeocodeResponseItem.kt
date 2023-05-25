package com.chow.weatherapp.data.model.geocode

import com.google.gson.annotations.SerializedName

data class GeocodeResponseItem(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("local_names")
    val localNames: LocalNames? = null,
    @SerializedName("lon")
    val lon: Double? = null,
    @SerializedName("name")
    val name: String? = null
)