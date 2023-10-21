package com.example.weatherforecast.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.weatherforecast.model.favorite.Favorite
import com.example.weatherforecast.widget.WeatherAppBar
import com.example.weatherforecast.R

@Composable
fun FavoritesScreen(navController: NavHostController) {
    FavoriteContent(navController)
}

@Composable
fun FavoriteContent(
    navController: NavHostController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "Favorite Cities",
                icon = Icons.Default.ArrowBack,
                elevation = 4.dp,
                isHomeScreen = false
            ) {
                navController.popBackStack()
            }
        }
    ) { pv ->
        Surface(modifier = Modifier.padding(pv)) {
            FavoriteList(favList = viewModel.favList.collectAsState().value, viewModel) { city ->
                navController.previousBackStackEntry?.savedStateHandle?.set("Search_key", city)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun FavoriteList(favList: List<Favorite>, viewModel: FavoritesViewModel, favSelected: (String) -> Unit) {

    LazyColumn(
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = favList.reversed()) { favItem ->

            Surface(
                shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
                elevation = 4.dp,
                color = colorResource(id = R.color.teal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable {
                               favSelected(favItem.city)
                    },
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = favItem.city,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.body1
                    )
                    Surface(
                        modifier = Modifier.padding(10.dp),
                        color = Color.LightGray,
                        shape = CircleShape
                    ) {
                        Text(
                            text = favItem.country,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(4.dp)
                            )
                    }

                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete Icon",
                        tint = Color.Red.copy(alpha = 0.5f),
                        modifier = Modifier.clickable {
                            viewModel.removeFromFavorite(favItem)
                        }
                    )
                }
            }

        }
    }
}
