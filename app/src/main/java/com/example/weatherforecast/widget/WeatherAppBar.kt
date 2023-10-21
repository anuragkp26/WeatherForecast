package com.example.weatherforecast.widget

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherforecast.navigation.WeatherForecastScreens

//@Preview(showBackground = true)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isHomeScreen: Boolean = true,
    navController: NavHostController,
    elevation: Dp = 0.dp,
    onAddActionItemClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    val showPopUpMenu = remember {
        mutableStateOf(false)
    }

    if (showPopUpMenu.value)
        ShowPopUpMenuDialog(showPopUpMenu, navController)

    TopAppBar(
        title = {

            val modifierTitile =

            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = if (isHomeScreen) TextAlign.Center else TextAlign.Start
            )
        },
        actions = {
            if (isHomeScreen) {
                IconButton(onClick = {
                    if (title.isNotEmpty())
                        onAddActionItemClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = {
                    showPopUpMenu.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More Icon"
                    )
                }
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Back button",
                    tint = Color.Black,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

@Composable
fun ShowPopUpMenuDialog(showPopUpMenu: MutableState<Boolean>, navController: NavHostController) {

    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf<String>("About", "Favorites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showPopUpMenu.value = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {

            items.forEachIndexed { index, itemText ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        showPopUpMenu.value = false
                    }
                ) {

                    Icon(
                        imageVector = when (itemText) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        },
                        contentDescription = "Icons",
                        tint = Color.LightGray
                    )

                    Text(
                        text = itemText,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clickable {
                                navController.navigate(
                                    route = when (itemText) {
                                        "About" -> WeatherForecastScreens.AboutScreen.name
                                        "Favorites" -> WeatherForecastScreens.FavoritesScreen.name
                                        else -> WeatherForecastScreens.SettingsScreen.name
                                    }
                                )
                            }
                    )
                }
            }

        }

    }
}
