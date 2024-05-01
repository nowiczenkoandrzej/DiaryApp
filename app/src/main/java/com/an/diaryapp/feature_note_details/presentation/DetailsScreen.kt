package com.an.diaryapp.feature_note_details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.an.diaryapp.feature_note_details.presentation.backgrounds.CloudyBackground
import com.an.diaryapp.feature_note_details.presentation.backgrounds.RainShowerBackground
import com.an.diaryapp.feature_note_details.presentation.backgrounds.SunnyBackground
import com.an.diaryapp.feature_note_details.presentation.backgrounds.VeryCloudyBackground
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
                1, 2, 3 -> CloudyBackground()
                45, 48 -> VeryCloudyBackground()
                51, 53, 55, 80, 81, 82-> RainShowerBackground()

                /*56 -> R.drawable.ic_snowyrainy
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
                    .verticalScroll(rememberScrollState())
            ) {

                val location = note.location?: "unknown"


                note.weatherInfo?.let {
                    Thermometer(temperature = note.weatherInfo.temperature)
                }


                Spacer(modifier = Modifier.height(28.dp))

                Row {
                    
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )
                    Text(
                        text = location,
                        fontSize = 24.sp
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = note.timestamp.toString(),
                        fontSize = 24.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = note.description)
            }
        }




    }

}