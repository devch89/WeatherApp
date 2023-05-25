package com.chow.weatherapp.presentation.screens.forecast

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chow.weatherapp.data.model.weather.Weather

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastDetailsList(
    forecasts: Weather? = null
) {
    LazyColumn(modifier = Modifier.padding()) {
        itemsIndexed(
            items = listOf(forecasts)
        ) { _, sat ->
            sat?.let {
                ForecastItem(weather = it)
            } ?: Text(text = "No information provided for searched city.")

        }
    }
}
