package com.example.weatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherforecast.R
import com.example.weatherforecast.widget.WeatherAppBar

@Composable
fun AboutScreen(navController: NavHostController) {
    AboutContent(navController)
}

@Composable
fun AboutContent(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "About",
                isHomeScreen = false,
                elevation = 4.dp,
                icon = Icons.Default.ArrowBack
            ) {
                navController.popBackStack()
            }
        }
    ) { pv ->
        Column(
            modifier = Modifier.padding(pv).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_version),
                style = MaterialTheme.typography.subtitle2,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "Weather API by : csfsa ga arhgar erah ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
