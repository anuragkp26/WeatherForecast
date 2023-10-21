package com.example.weatherforecast.screens.home

import com.example.weatherforecast.model.WeatherResponse

data class WeatherState(
    val isLoading: Boolean = false,
    val weatherDta: WeatherResponse? = null,
    val errorMessage:String = ""
)
