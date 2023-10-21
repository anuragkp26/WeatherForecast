package com.example.weatherforecast.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WeatherImage(imageUri: String) {
    AsyncImage(
        model = imageUri,
        contentDescription = "Weather Image",
        modifier = Modifier.size(80.dp)
    )
}