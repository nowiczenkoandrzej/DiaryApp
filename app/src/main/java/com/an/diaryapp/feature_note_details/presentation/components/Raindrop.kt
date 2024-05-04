package com.an.diaryapp.feature_note_details.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import java.lang.Float.max
import java.lang.Float.min
import java.lang.System.currentTimeMillis
import java.util.Random

@Composable
fun Raindrop(
    modifier: Modifier
) {

    val randomDelay = (Random().nextFloat() * 1000).toLong()

    var startTime by remember { mutableStateOf(currentTimeMillis() + randomDelay) }

    val animateTween = remember { Animatable(0f) }

    /*val animateTween by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 600,
                easing = LinearEasing
            ),
            RepeatMode.Restart
        ),
        label = "Animate Rain Drop",

    )*/

    LaunchedEffect(Unit) {
        delay(randomDelay)
        animateTween.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 600,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }


    Canvas(modifier) {
        val width = size.width
        val x: Float = size.width / 2

        val scopeHeight = size.height - width / 2

        // gap of 2 lines
        val space = size.height / 2.2f + width / 2
        val spacePos = scopeHeight * animateTween.value
        val sy1 = spacePos - space / 2
        val sy2 = spacePos + space / 2

        val random = Random()

        val startingPosition = random.nextFloat()

        val lineHeightModifier = random.nextFloat()

        // line length
        val lineHeight = scopeHeight - space

        // line1
        val line1y1 = max(0f, sy1 - lineHeight)
        val line1y2 = max(line1y1, sy1)

        // line2
        val line2y1 = min(sy2, scopeHeight)
        val line2y2 = min(line2y1 + lineHeight, scopeHeight)


        drawLine(
            color = Color.Black,
            start = Offset(x, line1y1),
            end = Offset(x, line1y2),
            strokeWidth = width,
            colorFilter = ColorFilter.tint(
                Color.Black
            ),
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.Black,
            start = Offset(x, line2y1),
            end = Offset(x, line2y2),
            strokeWidth = width,
            colorFilter = ColorFilter.tint(
                Color.Black
            ),
            cap = StrokeCap.Round
        )
    }

}

@Preview
@Composable
fun rainDropPrev() {
    Raindrop(modifier = Modifier.fillMaxSize())
}