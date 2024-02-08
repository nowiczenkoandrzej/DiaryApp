package com.an.diaryapp.feature_add_note.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.core.data.toWeatherIcon
import com.an.diaryapp.core.domain.model.WeatherInfo

@Composable
fun WeatherInfoPanel(
    modifier: Modifier = Modifier,
    location: String? = null,
    weatherInfo: WeatherInfo? = null,
    onTrackLocationClick: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Column {
            val locationText = if (location.isNullOrEmpty()) "no info" else location
            Text(text = "Location: $locationText")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                weatherInfo?.let {
                    Text(text = "Temperature: ${weatherInfo.temperature}")
                    Image(
                        painter = painterResource(id = weatherInfo.weatherType.toWeatherIcon()) ,
                        contentDescription = null,
                        modifier = Modifier.height(20.dp)
                    )
                }

            }

        }
        Spacer(modifier = Modifier.weight(1F))
        IconButton(onClick = { onTrackLocationClick() }) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null
            )
        }

    }
    


}