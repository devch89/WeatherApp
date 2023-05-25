package com.chow.weatherapp.presentation.screens.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chow.weatherapp.R
import com.chow.weatherapp.data.model.weather.Weather
import com.chow.weatherapp.util.WeatherUtil
import com.chow.weatherapp.util.WeatherUtil.roundOfToTwo

@Composable
fun ForecastItem(weather: Weather) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        WeatherUtil.IMAGE_ENDPOINT +
                                weather.weather?.get(0)?.icon +
                                "@2x.png"
                    )
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.no_image_load),
                contentDescription = weather.weather?.get(0)?.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(150.dp)
                    .width(150.dp),
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Temperature: ${weather.main?.temp?.roundOfToTwo().toString()}F"
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Today's High: ${weather.main?.tempMax?.roundOfToTwo().toString()}F"
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Today's Low: ${weather.main?.tempMin?.roundOfToTwo().toString()}F"
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Temperature Feels Like: ${
                    weather.main?.feelsLike?.roundOfToTwo().toString()
                }F"
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Humidity: ${weather.main?.humidity.toString()}%"
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Wind Speed: ${weather.wind?.speed.toString()} mph"
            )

        }

    }
}