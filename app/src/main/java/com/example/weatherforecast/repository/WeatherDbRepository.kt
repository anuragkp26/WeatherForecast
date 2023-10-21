package com.example.weatherforecast.repository

import com.example.weatherforecast.data.WeatherDatabaseDao
import com.example.weatherforecast.model.favorite.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val weatherDao: WeatherDatabaseDao
){

    fun getAllFavorites() : Flow<List<Favorite>> = weatherDao.getAllFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite = favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite = favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite = favorite)
    suspend fun getFavoriteByCity(city:String): Favorite = weatherDao.getFavById(city= city)

}