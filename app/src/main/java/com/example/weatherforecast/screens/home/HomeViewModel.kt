package com.example.weatherforecast.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository
import com.example.weatherforecast.data.DataState
import com.example.weatherforecast.model.favorite.Favorite
import com.example.weatherforecast.repository.WeatherDbRepository
import com.example.weatherforecast.utils.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val dbRepository: WeatherDbRepository
) : ViewModel() {

    private val _weatherData: MutableLiveData<DataState<WeatherResponse>> = MutableLiveData()
    val weatherData: LiveData<DataState<WeatherResponse>>
        get() = _weatherData

    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    private val _isFavorite: MutableStateFlow<Favorite> = MutableStateFlow(Favorite("", ""))
    val isFavorite = _isFavorite.asStateFlow()


    fun getWeather(place: String) {
        Log.e("WEATHER::", "HomeViewModel: getWeather")
        setStateEvent(HomeEvent.GetWeatherData(place, ""))
        isFavorite(place.toLowerCase().capitalize())
    }

    fun addToFavorite(city: String, country: String) = viewModelScope.launch {
        val fav = Favorite(city = city, country = country)

        dbRepository.insertFavorite(fav)
        isFavorite(city = city)

    }

    fun isFavorite(city: String) = viewModelScope.launch {
        //setStateEvent(HomeEvent.IsFavorite(city = city))
        Log.e("FAV::", "vm city: $city ")
        Log.e("FAV::", "vm 1: ${_isFavorite.value} ")
        _isFavorite.value = dbRepository.getFavoriteByCity(
            city
        )
        Log.e("FAV::", "vm 2: ${_isFavorite.value} ")
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun setStateEvent(stateEvent: HomeEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (stateEvent) {
                is HomeEvent.GetWeatherData -> {
                    repository.getWeatherData(
                        stateEvent.place,
                        stateEvent.unit
                    ).onEach {
                        //_weatherData.value = it
                        when(it) {
                            is DataState.Error -> {
                            //WeatherState(errorMessage = it.exception.message!!)
                                _weatherState.value = weatherState.value.copy(
                                    errorMessage = it.exception.message!!
                                )
                            }
                            is DataState.Loading -> {
                                _weatherState.value = weatherState.value.copy(
                                    isLoading = it.loading,
                                    errorMessage = ""
                                )
                            }
                            is DataState.Success -> {
                                _weatherState.value = weatherState.value.copy(
                                    weatherDta = it.data,
                                    errorMessage = ""
                                )
                            }
                        }.exhaustive
                    }.launchIn(viewModelScope)
                }
                is HomeEvent.IsFavorite -> {
                    _isFavorite.value = dbRepository.getFavoriteByCity(
                        stateEvent.city
                    )
                    Log.e("FAV::", "vm: ${_isFavorite.value} ")
                }
            }
        }
    }

}

sealed class HomeEvent {
    data class GetWeatherData(val place: String, val unit: String) : HomeEvent()
    data class IsFavorite(val city: String) : HomeEvent()
}