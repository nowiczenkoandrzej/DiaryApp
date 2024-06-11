package com.an.diaryapp.feature_note_list.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.presentation.components.CategoryItem
import com.an.diaryapp.core.presentation.components.WeatherDisplay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteDisplay(
    noteItem: NoteItem,
    onClick: (NoteItem) -> Unit,
    onLongClick: () -> Unit,
) {

    val localDensity = LocalDensity.current


    // Create element height in dp state
    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }


    val displayedDay = if (noteItem.timestamp.dayOfMonth < 10) {
        "0${noteItem.timestamp.dayOfMonth}"
    } else {
        noteItem.timestamp.dayOfMonth.toString()
    }

    // DATE NUMBER

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick(noteItem) },
                onLongClick = { onLongClick() }
            )
            .onGloballyPositioned { coordinates ->
                columnHeightDp = with(localDensity) {
                    coordinates.size.height.toDp()
                }
            },
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = displayedDay,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(8.dp)
        )


        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)

        ) {

            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = noteItem.description,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    noteItem.weatherInfo?.let { weatherInfo ->
                        WeatherDisplay(
                            weatherInfo = weatherInfo,
                            location = noteItem.location ?: "",
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                    Row {
                        noteItem.categories.forEach { CategoryItem(category = it) }
                    }
                }

            }

        }

    }


}