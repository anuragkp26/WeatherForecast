package com.example.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecast.data.WeatherDataBase
import com.example.weatherforecast.network.WeatherWebServices
import com.example.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideWeatherWebservice() : WeatherWebServices {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherWebServices::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context) : WeatherDataBase =
            Room.databaseBuilder(
                context,
                WeatherDataBase::class.java,
                WeatherDataBase.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDataBase: WeatherDataBase) = weatherDataBase.weatherDao()
}