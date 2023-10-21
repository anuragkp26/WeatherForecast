package com.example.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.screens.about.AboutScreen
import com.example.weatherforecast.screens.favorite.FavoritesScreen
import com.example.weatherforecast.screens.home.HomeScreen
import com.example.weatherforecast.screens.search.SearchScreen
import com.example.weatherforecast.screens.settings.SettingsScreen
import com.example.weatherforecast.screens.splash.SplashScreen

@Composable
fun WeatherForecastNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherForecastScreens.SplashScreen.name ) {

        composable(route = WeatherForecastScreens.SplashScreen.name){
            SplashScreen(navController)
        }
        composable(route = WeatherForecastScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(route = WeatherForecastScreens.SearchScreen.name) {
            SearchScreen(navController)
        }
        composable(route = WeatherForecastScreens.FavoritesScreen.name) {
            FavoritesScreen(navController)
        }
        composable(route = WeatherForecastScreens.AboutScreen.name) {
            AboutScreen(navController)
        }
        composable(route = WeatherForecastScreens.SettingsScreen.name) {
            SettingsScreen(navController)
        }
    }
}