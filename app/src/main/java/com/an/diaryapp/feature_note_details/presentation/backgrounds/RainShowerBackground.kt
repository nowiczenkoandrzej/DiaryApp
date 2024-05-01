package com.an.diaryapp.feature_note_details.presentation.backgrounds

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.ui.theme.BackgroundCloudySky
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

        repeat(30) {

            val min = 200.0f
            val max = 320.0f
            val randomStart = Random().nextFloat() * (max - min) + min

            val rainOffsetX = infiniteTransition.animateFloat(
                initialValue = randomStart,
                targetValue = randomStart - 30f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 300),
                    repeatMode = RepeatMode.Restart
                )
            )

            val rainOffsetY = infiniteTransition.animateFloat(
                initialValue = cloudOffset1.value,
                targetValue = cloudOffset1.value + 50f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 300
                        0f at 0 with LinearEasing // Initial position
                        50f at 300 // End position
                    },
                    repeatMode = RepeatMode.Restart
                )
            )

            Image(
                painter = rainDropPainter,
                contentDescription = null,
                modifier = Modifier
                    .offset(
                        x = rainOffsetX.value.dp,
                        y = rainOffsetY.value.dp
                    )
            )

        }


        Image(
            painter = cloudPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .offset(
                    x = cloudOffset1.value.dp,
                    y = (screenWidth / 7).dp
                )
        )

        Image(
            painter = cloudPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .offset(
                    x = cloudOffset2.value.dp,
                    y = (screenWidth / 9).dp
                )
        )



    }


}


