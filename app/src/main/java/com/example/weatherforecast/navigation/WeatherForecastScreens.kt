package com.example.weatherforecast.navigation

enum class WeatherForecastScreens {
    SplashScreen,
    HomeScreen,
    SearchScreen,
    FavoritesScreen,
    AboutScreen,
    SettingsScreen;
    companion object{
        fun fromRoute(route: String?) : WeatherForecastScreens =
            when(route?.substringBefore("/")){
                SplashScreen.name -> SplashScreen
                HomeScreen.name -> HomeScreen
                SearchScreen.name -> SearchScreen
                FavoritesScreen.name -> FavoritesScreen
                AboutScreen.name -> AboutScreen
                SettingsScreen.name -> SettingsScreen
                null -> HomeScreen
                else -> throw java.lang.IllegalArgumentException("Route $route is not recognised")
            }
    }
}