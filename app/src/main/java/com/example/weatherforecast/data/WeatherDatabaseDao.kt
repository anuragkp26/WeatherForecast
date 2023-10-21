package com.example.weatherforecast.data

import androidx.room.*
import com.example.weatherforecast.model.favorite.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDatabaseDao {

    @Query("SELECT * from fav_tbl")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}