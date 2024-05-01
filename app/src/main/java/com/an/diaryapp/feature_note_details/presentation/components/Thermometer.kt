package com.an.diaryapp.feature_note_details.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Thermometer(
    temperature: Double
) {


    val temperatureType = when {
        temperature > 22.0 -> {
            TemperatureType(
                color = Color.Red,
                thermometerHigh = 405f,
                distance = 135F
            )
        }
        temperature < 5.0 -> {
            TemperatureType(
                color = Color.Blue,
                thermometerHigh = 135f,
                distance = 405f
            )
        }
        else -> {
            TemperatureType(
                color = Color(0xFFFAB10C),
                thermometerHigh = 270f,
                distance = 270f
            )
        }
    }

    /*val animation = animateFloatAsState(
        targetValue = temperatureType.thermometerHigh,
        animationSpec = tween(durationMillis = 1000)
    ).value*/

    val animatedHeight = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(Unit){
        animatedHeight.animateTo(
            targetValue = temperatureType.thermometerHigh,
            animationSpec = tween(1000))
    }


    Box(
        modifier = Modifier
            .height(200.dp)
            .width(150.dp)
            .drawBehind {

                drawRoundRect(
                    color = Color.Black,
                    size = Size(
                        width = 64f,
                        height = 544f
                    ),
                    topLeft = Offset(
                        x = 98f,
                        y = 38f
                    ),
                    cornerRadius = CornerRadius(x = 8f, y = 8f)
                )

                drawCircle(
                    color = Color.Black,
                    radius = 62f,
                    center = Offset(
                        x = 130f,
                        y = 570f
                    )
                )

                drawRoundRect(
                    color = Color.LightGray,
                    size = Size(
                        width = 60f,
                        height = 540f
                    ),
                    topLeft = Offset(
                        x = 100f,
                        y = 40f
                    ),
                    cornerRadius = CornerRadius(x = 8f, y = 8f)
                )

                drawCircle(
                    color = Color.LightGray,
                    radius = 60f,
                    center = Offset(
                        x = 130f,
                        y = 570f
                    )
                )


                drawRoundRect(
                    color = temperatureType.color,
                    size = Size(
                        width = 40f,
                        height = animatedHeight.value
                    ),
                    topLeft = Offset(
                        x = 110f,
                        y = temperatureType.distance + temperatureType.thermometerHigh - animatedHeight.value
                    ),
                    cornerRadius = CornerRadius(x = 8f, y = 8f)
                )
                /*translate(
                    top = 100.dp.toPx()*translateBy.value
                ) {
                }*/

                drawCircle(
                    color = temperatureType.color,
                    radius = 50f,
                    center = Offset(
                        x = 130f,
                        y = 570f
                    )
                )

                repeat(7) {

                    val lineDistance = it * 67.5F + 67.5F

                    drawRoundRect(
                        color = Color.Black,
                        size = Size(
                            width = 36f,
                            height = 6f
                        ),
                        topLeft = Offset(
                            x = 128f,
                            y = lineDistance - 6f
                        ),
                        cornerRadius = CornerRadius(x = 4f, y = 4f)
                    )
                }

                drawRoundRect(
                    color = Color.Black,
                    size = Size(
                        width = 60f,
                        height = 12f
                    ),
                    topLeft = Offset(
                        x = 120f,
                        y = temperatureType.distance - 6f
                    ),
                    cornerRadius = CornerRadius(x = 4f, y = 4f)
                )

            }
    ){
        Text(
            text = "$temperature℃",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(
                    start = 64.dp,
                    top = (temperatureType.distance / 3 - 16).dp
                )
        )
    }
}


/*@Preview
@Composable
fun prev() {
    Thermometer(temperature = 100.0)
}*/


data class TemperatureType (
    val color: Color,
    val thermometerHigh: Float,
    val distance: Float
    
)