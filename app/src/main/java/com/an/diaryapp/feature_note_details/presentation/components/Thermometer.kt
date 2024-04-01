package com.an.diaryapp.feature_note_details.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp


@Composable
fun Thermometer(
    //modifier: Modifier = Modifier,
    temperature: Float
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .drawBehind {

            }
    ){

    }
}