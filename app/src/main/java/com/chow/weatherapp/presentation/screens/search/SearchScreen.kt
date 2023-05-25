package com.chow.weatherapp.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chow.weatherapp.HandlePermissions
import com.chow.weatherapp.presentation.topbar.search.SearchTopBar
import com.chow.weatherapp.presentation.viewmodel.BaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    baseViewModel: BaseViewModel,
    navController: NavController? = null
) {
    var getLocation = remember { mutableStateOf(false) }
    val citySearch = remember { mutableStateOf(TextFieldValue(baseViewModel.searchedCity ?: "")) }
    Scaffold(
        topBar = {
            SearchTopBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = citySearch.value,
                onValueChange = { txt ->
                    baseViewModel.isLocation.value?.let {
                        if (it) {
                            baseViewModel.searchedCity?.let { city ->
                                citySearch.value = TextFieldValue(city)
                            } ?: let {
                                baseViewModel.searchedCity = citySearch.value.text
                                citySearch.value = txt
                            }
                        } else {
                            baseViewModel.searchedCity = citySearch.value.text
                            citySearch.value = txt
                        }
                    } ?: let {
                        baseViewModel.searchedCity = citySearch.value.text
                        citySearch.value = txt
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (citySearch.value.text.isNotEmpty()) {
                            baseViewModel.searchedCity = citySearch.value.text
                            navController?.navigate("details")
                        } else {

                        }

                    }) {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                },
                placeholder = { Text(text = "Enter City Name") },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                ),
                maxLines = 1,
                singleLine = true,
            )
            Button(
                onClick = {
                    getLocation.value = true
                    // navigate to details
//                navController?.navigate("details")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Location",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "WeatherByLocation")
            }
        }
        if (getLocation.value) {
            HandlePermissions(baseViewModel)
            getLocation.value = false
        }
    }
}