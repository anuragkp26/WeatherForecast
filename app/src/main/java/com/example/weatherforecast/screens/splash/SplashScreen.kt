package com.example.weatherforecast.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherforecast.R
import com.example.weatherforecast.navigation.WeatherForecastScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    SplashContent(navController)

}

//@Preview(showBackground = true)
@Composable
fun SplashContent(navController: NavHostController) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {

        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(
                durationMillis =1800,
                easing = {
                    OvershootInterpolator(4f)
                        .getInterpolation(it)
                }
            )
        )
        delay(1500)
        navController.navigate(WeatherForecastScreens.HomeScreen.name) {
            popUpTo(route = WeatherForecastScreens.SplashScreen.name){
                inclusive = true
            }
        }
    })

    Surface(
        modifier = Modifier
            .padding(18.dp)
            .size(330.dp)
            .scale(scale = scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "sun",
                modifier = Modifier.size(95.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "Weather Forecast",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray
            )
        }
    }
}