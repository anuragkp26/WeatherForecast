package com.example.weatherforecast.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherforecast.widget.WeatherAppBar

@Composable
fun SearchScreen(navController: NavHostController) {
    SearchSContent(navController)
}

@Composable
fun SearchSContent(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "Search",
                elevation = 4.dp,
                icon = Icons.Default.ArrowBack,
                isHomeScreen = false,
            ) {
                navController.popBackStack()
            }
        }
    ) { pv ->
        Column(modifier =  Modifier.padding(pv)) {
            SearchBox() {search ->
                navController.previousBackStackEntry?.savedStateHandle?.set("Search_key", search)
                navController.popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(onSearch: (String) -> Unit = {}) {

    val searchQueryState =  remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val validate = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    
    CommonTextField(
        valueState = searchQueryState,
        placeHolder = "Search",
        imeAction = ImeAction.Search,
        onAction = KeyboardActions {
            if(validate)
                onSearch(searchQueryState.value)
            keyboardController?.hide()
        }
    )
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeHolder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray
        ),
        shape = RoundedCornerShape(14.dp),
        label = {
            Text(text = placeHolder)
        },
        keyboardActions = onAction
    )
}
