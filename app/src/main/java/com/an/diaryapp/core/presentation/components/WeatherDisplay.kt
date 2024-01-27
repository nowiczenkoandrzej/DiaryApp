package com.an.diaryapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.core.data.toWeatherIcon
import com.an.diaryapp.core.domain.model.WeatherInfo

@Composable
fun WeatherDisplay(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo,
    location: String
) {

    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = location)

        Spacer(modifier = Modifier.weight(1F))

        Text(text = "${weatherInfo.temperature}\u2103")

        Spacer(modifier = Modifier.width(4.dp))

        Image(
            painter = painterResource(id = weatherInfo.weatherType.toWeatherIcon()) ,
            contentDescription = null,
            modifier = Modifier.height(20.dp)
        )

    }


}