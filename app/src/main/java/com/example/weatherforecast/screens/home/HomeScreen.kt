package com.example.weatherforecast.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherforecast.R
import com.example.weatherforecast.component.CircularProgressbar
import com.example.weatherforecast.component.WeatherImage
import com.example.weatherforecast.model.Day
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.navigation.WeatherForecastScreens
import com.example.weatherforecast.data.DataState
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDateTime
import com.example.weatherforecast.utils.formatDecimal
import com.example.weatherforecast.widget.ListItemDay
import com.example.weatherforecast.widget.WeatherAppBar


@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    HomeContent(navController, viewModel)

    val searchScreenResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("Search_key")?.observeAsState()

    LaunchedEffect(key1 = true) {

        if(searchScreenResult?.value != null) {
            searchScreenResult.value?.let {
                Log.e("SEARCH::", "Search_key: $it", )
                viewModel.getWeather(it)
                //viewModel.isFavorite(it)
            }
        } else {
            viewModel.getWeather("Bali")
            //viewModel.isFavorite("Bali")
        }

    }

}

@Composable
fun HomeContent(navController: NavHostController? = null, viewModel: HomeViewModel) {

   /* var progressState by remember {
        mutableStateOf(false)
    }
    var weatherState by remember {
        mutableStateOf<WeatherResponse?>(null)
    }*/

    /*var placeState by remember {
        mutableStateOf("")
    }*/

    var showToast by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    //val weatherData = viewModel.weatherData.observeAsState()

    val fav = viewModel.isFavorite.collectAsState().value
    Log.e("FAV::", "Homw 1fav: $fav ")


    val weatherStateVm = viewModel.weatherState.value

    if(weatherStateVm.errorMessage.isNotEmpty()){
        Toast.makeText(context, weatherStateVm.errorMessage, Toast.LENGTH_LONG).show()
    }


    /*weatherData.value.let { dataState ->
        when (dataState) {
            is DataState.Success -> {
                Log.e("WEATHER::", "Data: ")
                //Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                weatherState = dataState.data
                placeState = dataState.data.city.name + ", " + dataState.data.city.country
            }
            is DataState.Error -> {
                Log.e("WEATHER::", "Error: ${dataState.exception.message}")
                Toast.makeText(context, dataState.exception.message, Toast.LENGTH_LONG).show()
            }
            is DataState.Loading -> {
                Log.e("WEATHER::", "loading: ${dataState.loading}")
                progressState = dataState.loading
            }
            null -> {
                Log.e("WEATHER::", "NULL")
            }
        }
    }*/

    if(fav?.city?.isNotEmpty() == true) {
      showToast = false
    }

    if(showToast){
        val context = LocalContext.current
        Toast.makeText(context, "Added to favorite", Toast.LENGTH_LONG).show()
    }

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = if(weatherStateVm.weatherDta != null )
                    weatherStateVm.weatherDta?.city?.name + ", " + weatherStateVm.weatherDta?.city?.country
                else "",//placeState,
                elevation = 4.dp,
                navController = navController!!,
                onAddActionItemClicked = {
                    navController.navigate(route = WeatherForecastScreens.SearchScreen.name)
                },
                icon = if(fav == null || fav.city.isEmpty()) Icons.Default.FavoriteBorder else null
            ) {
                /*weatherState*/weatherStateVm.weatherDta?.let {
                    viewModel.addToFavorite(it.city.name, it.city.country).run {
                        showToast = true
                    }
                }

            }
        }
    ) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                if (/*progressState*/weatherStateVm.isLoading) {
                    CircularProgressbar()
                }
                HomeView(/*weatherState*/weatherStateVm.weatherDta)

            }
        }
    }
}

@Composable
fun HomeView(weatherState: WeatherResponse?) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        weatherState?.let {

            val weatherToday = it.list[0]
            val imageUrl = "https://openweathermap.org/img/wn/${weatherToday.weather[0].icon}.png"

            Text(
                text = formatDate(weatherToday.dt),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )

            Surface(
                modifier = Modifier
                    .padding(6.dp)
                    .size(200.dp),
                shape = CircleShape,
                color = colorResource(id = R.color.yellow)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherImage(imageUri = imageUrl)
                    Text(
                        text = "${formatDecimal(weatherToday.temp.day)}\u00B0 ",
                        style = MaterialTheme.typography.h4,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = weatherToday.weather[0].main,
                        style = MaterialTheme.typography.body1,
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp,
                    )
                }
            }

            HumidityWindPressureRow(weatherToday)
            Divider()
            SunRiseAndSetRow(weatherToday)
            Text(
                text = "This Week",
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            WeatherList(it.list)

        }


    }
}

@Composable
fun WeatherList(list: List<Day>) {

    LazyColumn(modifier = Modifier.padding(4.dp), contentPadding = PaddingValues(1.dp)) {
        items(items = list) {day ->
            ListItemDay(day)
        }
    }

}

@Composable
fun SunRiseAndSetRow(weatherToday: Day) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        IconTextRow(R.drawable.sunrise, formatDateTime(weatherToday.sunrise), true, 30.dp)
        IconTextRow(R.drawable.sunset, formatDateTime(weatherToday.sunset), false, 30.dp)

    }
}

@Composable
fun HumidityWindPressureRow(weatherToday: Day) {

    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        IconTextRow(R.drawable.humidity, "${weatherToday.humidity}%", true, 20.dp)
        IconTextRow(R.drawable.pressure, "${weatherToday.pressure} psi%", true, 20.dp)
        IconTextRow(R.drawable.wind, "${weatherToday.speed}mph", true, 20.dp)

    }
}

@Composable
fun IconTextRow(drawableId: Int, text: String, isIconFirst: Boolean, iconSize: Dp) {

    Row(modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
        if (isIconFirst) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "icon",
                modifier = Modifier.size(size = iconSize),
                contentScale = ContentScale.Fit
            )
        }
        Text(
            text = text,
            fontSize = 14.sp,
            style = MaterialTheme.typography.caption
        )
        if (!isIconFirst) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "icon",
                modifier = Modifier.size(size = iconSize),
                contentScale = ContentScale.Fit
            )
        }
    }
}




