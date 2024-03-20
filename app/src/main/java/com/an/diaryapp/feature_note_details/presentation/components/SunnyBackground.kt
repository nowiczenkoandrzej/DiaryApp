package com.an.diaryapp.feature_note_details.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SunnyBackground() {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {

               /* val path = Path().apply {
                    lineTo(screenWidth.toFloat(), 100F)
                }*/
                drawRect(
                    color = Color.Blue
                )
                //drawPath(path = path, color = Color.White)
                drawCircle(
                    radius = 100f,
                    color = Color.Yellow,
                    center = Offset(
                        x = screenWidth.toFloat()*2,
                        y = screenWidth.toFloat()
                    )
                )




            }
    ) {

    }
}

@Preview
@Composable
fun sunnyBackgroundPreview() {
    SunnyBackground()
}