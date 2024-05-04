package com.an.diaryapp.feature_note_details.presentation.backgrounds

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.feature_note_details.presentation.components.Raindrop
import com.an.diaryapp.feature_note_details.presentation.components.RainshowerAnimation
import com.an.diaryapp.ui.theme.BackgroundCloudySky
import kotlinx.coroutines.CoroutineScope
import java.util.Random

@Composable
fun RainShowerBackground() {


    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val cloudPainter = painterResource(id = R.drawable.ic_cloudy)
    val rainDropPainter = painterResource(id = R.drawable.baseline_water_drop_12)

    val infiniteTransition = rememberInfiniteTransition()

    val cloudOffset1 = infiniteTransition.animateFloat(
        initialValue = screenWidth.toFloat() / 1.5f,
        targetValue = screenWidth.toFloat() / 3,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Log.d("TAG", "RainShowerBackground: ${screenWidth.toFloat()}")

    val cloudOffset2 = infiniteTransition.animateFloat(
        initialValue = screenWidth.toFloat() / 3,
        targetValue = screenWidth.toFloat() / 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000),
            repeatMode = RepeatMode.Reverse
        )
    )




    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = BackgroundCloudySky)
            }
    ) {

        RainshowerAnimation(
            modifier = Modifier
                .offset(
                    x = (screenWidth / 2).dp,
                    y = (screenHeight / 6).dp
                ),
        )




        Image(
            painter = cloudPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .offset(
                    x = cloudOffset1.value.dp,
                    y = (screenHeight / 20).dp
                )
        )

        Image(
            painter = cloudPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .offset(
                    x = cloudOffset2.value.dp,
                    y = (screenHeight / 24).dp
                )
        )






    }


}


