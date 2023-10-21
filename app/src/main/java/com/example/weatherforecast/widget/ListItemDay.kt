package com.example.weatherforecast.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.R
import com.example.weatherforecast.component.WeatherImage
import com.example.weatherforecast.model.Day
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDecimal

//@Preview(showBackground = true)
@Composable
fun ListItemDay(day: Day) {

    val imageUrl = "https://openweathermap.org/img/wn/${day.weather[0].icon}.png"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),//RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp, bottomEnd = 30.dp),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.padding( horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatDate(day.dt).substringBefore(","), modifier = Modifier.padding(start = 4.dp))
            WeatherImage(imageUri = imageUrl)
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier.padding(4.dp),
                    color = colorResource(id = R.color.yellow),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = day.weather[0].description,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontSize = 18.sp,
                    )
                ){
                    append("${formatDecimal(day.temp.max)}°")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray,
                        fontSize = 18.sp,
                    )
                ){
                    append("${formatDecimal(day.temp.min)}°")
                }
            })
        }

    }
}