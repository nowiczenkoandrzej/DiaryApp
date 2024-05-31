package com.an.diaryapp.feature_note_list.presentation.components

import android.util.Range
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.feature_note_list.domain.model.FilterType
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteringSheet(
    categories: List<Category>,
    onSaveFilters: (FilterType) -> Unit
) {

    val selectedDateRange = remember {

        val startDate: LocalDate? = null
        val endDate: LocalDate? = null

        val value: Range<LocalDate>? = null
        mutableStateOf<Range<LocalDate>?>(value)
    }

    var selectedCategory: Category? = null

    val mainCalendarState = rememberSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            //horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
            /*HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 1.dp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Date range",
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 1.dp,
                modifier = Modifier.weight(1f)
            )*/


            Text(text = "Select Filters")
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(onClick = {

                val filterType = FilterType(
                    startDate = selectedDateRange.value?.lower,
                    endDate = selectedDateRange.value?.upper,
                    category = selectedCategory
                )

                onSaveFilters(filterType)
            }) {
                Text(text = "Save")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        CalendarView(
            sheetState = mainCalendarState,
            config = CalendarConfig(
                style = CalendarStyle.MONTH
            ),
            selection = CalendarSelection.Period(
                selectedRange = selectedDateRange.value
            ) { startDate, endDate ->
                selectedDateRange.value = Range(startDate, endDate)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CategoryPicker(
            modifier = Modifier.fillMaxWidth(),
            categories = categories,
            onSelect = {
                selectedCategory = Category(backgroundColor = "", name = it)
            }
        )

    }
}