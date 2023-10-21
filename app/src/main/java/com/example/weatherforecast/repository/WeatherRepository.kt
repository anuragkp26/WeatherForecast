package com.example.weatherforecast.repository

import android.util.Log
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.network.WeatherWebServices
import com.example.weatherforecast.data.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class WeatherRepository @Inject constructor(
    private val weatherWebServices: WeatherWebServices
) {

    suspend fun getWeatherData(
        place: String,
        unit: String
    ): Flow<DataState<WeatherResponse>> = flow {

        try {
            emit(DataState.Loading(true))
            val response = weatherWebServices.getWeatherData(place = place)
            if(response.isSuccessful){
                Log.e("WEATHER::", "API SUCCESS: ")
                emit(DataState.Success(response.body()!!))
                delay(500L)
                emit(DataState.Loading(false))
            } else {
                Log.e("WEATHER::", "API FAILED: ${response.errorBody()?.string()}")
                val data = response.errorBody()!!.string()
                val jObjError = JSONObject(data)
                emit(DataState.Error(CancellationException(jObjError.getString("message"))))
                emit(DataState.Loading(false))
            }


        } catch (e: java.lang.Exception) {
            Log.e("WEATHER::", "API ERROR: ${e.message}")
            emit(DataState.Error(CancellationException(e.message)))
            delay(500L)
            emit(DataState.Loading(false))
        }
    }

}