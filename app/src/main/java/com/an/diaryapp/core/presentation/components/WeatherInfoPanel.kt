package com.an.diaryapp.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.an.diaryapp.core.domain.model.WeatherInfo

@Composable
fun WeatherInfoPanel(
    location: String? = null,
    weatherInfo: WeatherInfo? = null,
    onTrackLocationClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(text = "$location:")
        Text(text = "${weatherInfo?.temperature}:")
        Spacer(modifier = Modifier.weight(1F))
        IconButton(onClick = { onTrackLocationClick() }) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null
            )
        }

    }


}