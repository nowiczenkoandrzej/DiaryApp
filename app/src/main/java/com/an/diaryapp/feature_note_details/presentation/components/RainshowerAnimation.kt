package com.an.diaryapp.feature_note_details.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RainshowerAnimation(
    modifier: Modifier,
) {

    Row (
        modifier = modifier
    ){

        Raindrop(
            modifier = Modifier
                .width(20.dp)
                .height(112.dp)
                .rotate(30f)
                .padding(8.dp)
        )
        Raindrop(
            modifier = Modifier
                .width(20.dp)
                .height(124.dp)
                .rotate(30f)
                .padding(8.dp)
        )
        Raindrop(
            modifier = Modifier
                .width(20.dp)
                .height(104.dp)
                .rotate(30f)
                .padding(8.dp)
        )
        Raindrop(
            modifier = Modifier
                .width(20.dp)
                .height(120.dp)
                .rotate(30f)
                .padding(8.dp)
        )
        Raindrop(
            modifier = Modifier
                .width(20.dp)
                .height(108.dp)
                .rotate(30f)
                .padding(8.dp)
        )
        Raindrop(
            modifier = Modifier
                .width(20.dp)
                .height(112.dp)
                .rotate(30f)
                .padding(8.dp)
        )
        Raindrop(
            modifier = Modifier
                .width(20.dp)
                .height(120.dp)
                .rotate(30f)
                .padding(8.dp)
        )


    }
}