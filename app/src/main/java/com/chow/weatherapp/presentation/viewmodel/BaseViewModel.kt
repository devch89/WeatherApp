package com.chow.weatherapp.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chow.weatherapp.data.model.weather.Weather
import com.chow.weatherapp.data.repository.remote.WeatherRepository
import com.chow.weatherapp.util.UIState
import com.chow.weatherapp.util.WeatherUtil.LOCATION_KEY
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    var searchedCity: String?
        get() = sharedPreferences.getString(LOCATION_KEY, null)
        set(value) {
            sharedPreferences.edit {
                putString(LOCATION_KEY, value)
                apply()
            }
        }

    private val _forecast: MutableState<UIState<Weather>> = mutableStateOf(UIState.Loading)
    val forecast: State<UIState<Weather>> get() = _forecast

    private val _isLocation: MutableState<Boolean?> = mutableStateOf(null)
    val isLocation: State<Boolean?> get() = _isLocation

    fun getForecast() {
        viewModelScope.launch(ioDispatcher) {
            searchedCity?.let {
                weatherRepository.getForecast(it).collect { state ->
                    _forecast.value = state
                }
            } ?: let {
                _forecast.value = UIState.Error(Exception("City location not entered"))
            }
        }
    }

    private fun getCityNameFromLocation(location: Location) {
        viewModelScope.launch(ioDispatcher) {
            searchedCity = weatherRepository.getCityName(location.latitude, location.longitude)
            _isLocation.value = true
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val locationTask = fusedLocationProviderClient.lastLocation

        locationTask
            .addOnSuccessListener { location ->
                if (location != null) {
                    getCityNameFromLocation(location)
                }
            }
            .addOnFailureListener {
                _isLocation.value = false
            }
    }


}