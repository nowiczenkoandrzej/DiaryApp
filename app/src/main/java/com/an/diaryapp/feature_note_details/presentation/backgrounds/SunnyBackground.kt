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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.ui.theme.BackgroundSky
import com.an.diaryapp.ui.theme.BackgroundSun
import com.an.diaryapp.ui.theme.BackgroundSunshine

@Composable
fun SunnyBackground() {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val rotationDurationMillis: Int = 25000
    val rotationDegrees: Float = 360f

    val translationDurationMillis: Int = 2000
    val translationDistance: Dp = 100.dp


    val infiniteTransition = rememberInfiniteTransition()

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

    val translationState by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = translationDistance.value,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = translationDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {

                drawRect(
                    color = BackgroundSky
                )

                drawCircle(
                    radius = screenWidth.toFloat() * 2,
                    color = BackgroundSunshine,
                    center = Offset(
                        x = screenWidth.toFloat() * 3,
                        y = 0f
                    )
                )

                drawCircle(
                    radius = screenWidth.toFloat() * 3 / 2,
                    color = BackgroundSun,
                    center = Offset(
                        x = screenWidth.toFloat() * 3,
                        y = 0f
                    )
                )


            }
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_sunny),
            contentDescription = null,
            modifier = Modifier
                .size((screenWidth / 5 * 2).dp)
                .rotate(rotationState)
                .align(Alignment.TopEnd)

        )

    }
}

@Preview
@Composable
fun sunnyBackgroundPreview() {
    SunnyBackground()
}