package com.an.diaryapp.feature_note_details.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun CalendarPage(
    date: LocalDate
) {

    Card(
        modifier = Modifier
            .width(120.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .drawBehind {
                    drawRect(color = Color.White)
                }
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .drawBehind {
                        drawRect(
                            color = Color.Yellow
                        )
                    }

            ) {

                Text(
                    text = date.month.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = date.dayOfWeek.name)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${date.dayOfMonth}",
                fontSize = 36.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${date.year}",
                fontSize = 20.sp
            )





        }
    }


}