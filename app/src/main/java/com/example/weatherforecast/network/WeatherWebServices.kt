package com.example.weatherforecast.network

import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherWebServices {


    @GET("data/2.5/forecast/daily?")
    suspend fun getWeatherData(
        @Query("q") place : String,
        @Query("units") units : String = "imperial",
        @Query(value = "appid", encoded = true) apiKey : String = Constants.API_KEY
    ) : Response<WeatherResponse>
}