package com.an.diaryapp.feature_note_details.presentation.backgrounds

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.ui.theme.BackgroundCloudySky


@Composable
fun VeryCloudyBackground() {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val cloudPainter = painterResource(id = R.drawable.ic_cloudy)

    val infiniteTransition = rememberInfiniteTransition()

    val offsetX1 = infiniteTransition.animateFloat(
        initialValue = screenWidth.toFloat() / 1.5f,
        targetValue = screenWidth.toFloat() / 3,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offsetX2 = infiniteTransition.animateFloat(
        initialValue = screenWidth.toFloat() / 3,
        targetValue = screenWidth.toFloat() / 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5400),
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

        Image(
            painter = cloudPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .offset(
                    x = offsetX1.value.dp,
                    y = (screenWidth / 7).dp
                )


        )

        Image(
            painter = cloudPainter,
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .offset(
                    x = offsetX2.value.dp,
                    y = (screenWidth / 9).dp
                )


        )

    }


}