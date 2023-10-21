package com.example.weatherforecast.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.favorite.Favorite
import com.example.weatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dbRepository: WeatherDbRepository
) : ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch {
            dbRepository.getAllFavorites().distinctUntilChanged()
                .collect() {
                    _favList.value = it
                }
        }
    }

    fun removeFromFavorite(fav: Favorite) = viewModelScope.launch{
        dbRepository.deleteFavorite(fav)
    }

}