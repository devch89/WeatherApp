package com.chow.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chow.weatherapp.presentation.screens.forecast.ForecastDetailsList
import com.chow.weatherapp.presentation.screens.search.SearchScreen
import com.chow.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.chow.weatherapp.presentation.viewmodel.BaseViewModel
import com.chow.weatherapp.util.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val baseViewModel = hiltViewModel() as BaseViewModel
                    NavHost(navController = navController, startDestination = "main") {
                        composable(route = "main") {
                            SearchScreen(
                                baseViewModel = baseViewModel,
                                navController = navController
                            )
                        }
                        composable(route = "details") {
                            baseViewModel.getForecast()
                            ForecastDetails(baseViewModel = baseViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HandlePermissions(
    baseViewModel: BaseViewModel
) {
    // code to check permissions
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permsMap ->
        val areGranted = permsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            baseViewModel.getLocation()
        } else {

        }
    }

    // code to request
    checkRequestPermissions(
        LocalContext.current,
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        launcher,
        baseViewModel
    )
}

@Composable
fun checkRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    viewModel: BaseViewModel
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        viewModel.getLocation()
    } else {
        SideEffect {
            launcher.launch(permissions)
        }
    }
}

@Composable
fun ForecastDetails(
    baseViewModel: BaseViewModel
) {
    when (val state = baseViewModel.forecast.value) {
        is UIState.Error -> {}
        is UIState.Loading -> {}
        is UIState.Success -> {
            ForecastDetailsList(state.data)
        }
    }
}