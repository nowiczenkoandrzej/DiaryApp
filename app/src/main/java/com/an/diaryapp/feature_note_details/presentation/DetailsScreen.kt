package com.an.diaryapp.feature_note_details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.an.diaryapp.core.data.toWeatherIcon
import com.an.diaryapp.core.presentation.components.WeatherDisplay
import com.an.diaryapp.feature_note_details.presentation.components.CalendarPage
import com.an.diaryapp.feature_note_details.presentation.components.SunnyBackground
import com.an.diaryapp.feature_note_details.presentation.components.Thermometer

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel
) {


    val note = viewModel
        .note
        .collectAsState()
        .value

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        note?.weatherInfo?.let {
            when(note.weatherInfo.weatherType.toInt()) {
                0 -> SunnyBackground()
                /*1 -> R.drawable.ic_cloudy
                2 -> R.drawable.ic_cloudy
                3 -> R.drawable.ic_cloudy
                45 -> R.drawable.ic_very_cloudy
                48 -> R.drawable.ic_very_cloudy
                51-> R.drawable.ic_rainshower
                53 -> R.drawable.ic_rainshower
                55 -> R.drawable.ic_rainshower
                56 -> R.drawable.ic_snowyrainy
                57 -> R.drawable.ic_snowyrainy
                61 -> R.drawable.ic_rainy
                63 -> R.drawable.ic_rainy
                65 -> R.drawable.ic_rainy
                66 -> R.drawable.ic_snowyrainy
                67 -> R.drawable.ic_snowyrainy
                71 -> R.drawable.ic_snowy
                73 -> R.drawable.ic_heavysnow
                75 -> R.drawable.ic_heavysnow
                77 -> R.drawable.ic_heavysnow
                80 -> R.drawable.ic_rainshower
                81 -> R.drawable.ic_rainshower
                82 -> R.drawable.ic_rainshower
                85 -> R.drawable.ic_snowy
                86 -> R.drawable.ic_snowy
                95 -> R.drawable.ic_thunder
                96 -> R.drawable.ic_rainythunder
                99 -> R.drawable.ic_rainythunder*/
                else -> {

                }
            }
        }

        note?.let {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {

                val location = note.location?: "unknown"

                val temperature = if(note.weatherInfo == null) {
                    "..."
                } else {
                    note.weatherInfo.temperature.toString()
                }

                val weatherIcon: Painter? = if(note.weatherInfo == null) {
                    null
                } else {
                    painterResource(id = note.weatherInfo.weatherType.toWeatherIcon())
                }


                //CalendarPage(date = note.timestamp)
                note.weatherInfo?.let {
                    Thermometer(temperature = note.weatherInfo.temperature)
                }


                /*Text(
                    text = note.timestamp.toString(),
                    fontSize = 36.sp

                )*/

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Location: $location",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Weather: $temperature\u2103 ",
                        fontSize = 24.sp
                    )

                    weatherIcon?.let {
                        Image(
                            painter = weatherIcon,
                            contentDescription = null,
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = note.description)
            }
        }




    }

}