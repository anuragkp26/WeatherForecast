package com.example.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforecast.model.favorite.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDatabaseDao

    companion object {
        val DATABASE_NAME = "Weather_Room_Database"
    }
}