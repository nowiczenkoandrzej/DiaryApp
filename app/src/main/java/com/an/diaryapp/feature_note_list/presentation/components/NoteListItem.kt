package com.an.diaryapp.feature_note_list.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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

    ConstraintLayout(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick(noteItem) },
                onLongClick = { onLongClick() }
            )
    ) {

        val (dateNumber, categories, line, note) = createRefs()

        val displayedDay = if (noteItem.timestamp.dayOfMonth < 10) {
            "0${noteItem.timestamp.dayOfMonth}"
        } else {
            noteItem.timestamp.dayOfMonth.toString()
        }

        // DATE NUMBER

        Text(
            text = displayedDay,
            fontSize = 32.sp,
            modifier = Modifier
                .constrainAs(dateNumber) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }
                .padding(8.dp)
        )


        // LINE

        Column(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(dateNumber.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(dateNumber.end)
                }
                .height(columnHeightDp)
        ) {

            if(columnHeightDp > 100.dp) {
                Spacer(modifier = Modifier.height(8 .dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .width(6.dp)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .weight(1F)

                )
                Spacer(modifier = Modifier.height(20.dp))
            }

        }

        // CATEGORIES

        Row(
            modifier = Modifier
                .constrainAs(categories) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(dateNumber.end, margin = 8.dp)
                    bottom.linkTo(dateNumber.bottom)
                }
        ) {
            noteItem.categories.forEach { category ->
                CategoryItem(category = category)
            }
            if(noteItem.categories.isEmpty()) {
                Text(text = "")
            }
        }

        // NOTE

        Column(
            modifier = Modifier
                .constrainAs(note) {
                    top.linkTo(categories.bottom, margin = 8.dp)
                    start.linkTo(dateNumber.end, margin = 8.dp)

                }
                .onGloballyPositioned {
                    columnHeightDp = with(localDensity) {
                        if (it.size.height.toDp() < 60.dp)
                            60.dp
                        else
                            it.size.height.toDp()
                    }
                }
                .padding(end = 80.dp)
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

        }



    }



        /*Row(
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
                            .background(color = MaterialTheme.colorScheme.primary)
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
        }*/


}