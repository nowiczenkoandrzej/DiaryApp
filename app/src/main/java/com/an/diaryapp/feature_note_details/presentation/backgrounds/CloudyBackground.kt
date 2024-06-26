package com.an.diaryapp.feature_note_details.presentation.backgrounds

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.ui.theme.BackgroundSky

@Composable
fun CloudyBackground() {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val cloudPainter = painterResource(id = R.drawable.ic_cloudy)
    val sunnyPainter = painterResource(id = R.drawable.ic_sunny)


    val infiniteTransition = rememberInfiniteTransition()

    val offsetX = infiniteTransition.animateFloat(
        initialValue = screenWidth.toFloat() / 3,
        targetValue = screenWidth.toFloat() / 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rotationDurationMillis: Int = 25000
    val rotationDegrees: Float = 360f

    val rotationState by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = rotationDegrees,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = rotationDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = BackgroundSky)
            }
    ) {

        Image(
            painter = sunnyPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .rotate(rotationState)
                .align(Alignment.TopEnd)

        )

        Image(
            painter = cloudPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .offset(
                    x = offsetX.value.dp,
                    y = (screenWidth / 6).dp
                )


        )

    }
}

/*
@Preview
@Composable
fun cloudyBackgroundPreview() {
    CloudyBackground()
}*/
