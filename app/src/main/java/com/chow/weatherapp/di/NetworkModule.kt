package com.chow.weatherapp.di

import android.content.Context
import android.content.SharedPreferences
import com.chow.weatherapp.data.remote.geocoder.GeoCodeApi
import com.chow.weatherapp.data.remote.weather.WeatherApi
import com.chow.weatherapp.util.GeoCodeUtil
import com.chow.weatherapp.util.WeatherUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providesSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("LOCATION_SHARED_PREFS", Context.MODE_PRIVATE)


    @Provides
    fun providesFusedLocation(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    @Singleton
    @Provides
    fun providesWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(WeatherUtil.BASE_URL_FORECAST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun providesGeoEncodingApi(): GeoCodeApi {
        return Retrofit.Builder()
            .baseUrl(GeoCodeUtil.BASE_URL_GEO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeoCodeApi::class.java)
    }
}