package com.an.diaryapp.feature_note_list.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.presentation.components.CategoryItem
import com.an.diaryapp.core.presentation.components.WeatherDisplay
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListItem(
    noteItem: NoteItem,
    onClick: (NoteItem) -> Unit,
    onLongClick: () -> Unit,
) {
    val localDensity = LocalDensity.current

    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .combinedClickable(
                    onClick = { onClick(noteItem) },
                    onLongClick = { onLongClick() }
                ),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .height(columnHeightDp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val displayedDay = if (noteItem.timestamp.dayOfMonth < 10) {
                    "0${noteItem.timestamp.dayOfMonth}"
                } else {
                    noteItem.timestamp.dayOfMonth.toString()
                }

                Text(
                    text = displayedDay,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(8.dp)
                )
                if(columnHeightDp > 100.dp) {
                    Spacer(modifier = Modifier.height(8 .dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .width(6.dp)
                            .background(color = Color.LightGray)
                            .weight(1F)

                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }

            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(4.dp)
                    .onGloballyPositioned {
                        columnHeightDp = with(localDensity) {
                            if (it.size.height.toDp() < 60.dp)
                                60.dp
                            else
                                it.size.height.toDp()
                        }
                    }
            ) {
                Row {
                    noteItem.categories.forEach { category ->
                        CategoryItem(category = category)
                    }
                }
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
            }
        }
    }

}
