package com.an.diaryapp.feature_add_note.presentation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.an.diaryapp.core.domain.model.Screen
import com.an.diaryapp.feature_add_note.presentation.components.CategorySelector
import com.an.diaryapp.feature_add_note.presentation.components.WeatherInfoPanel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection


@RequiresApi(34)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: AddNoteViewModel
) {

    val categories = viewModel
        .categories
        .collectAsState()
        .value


    val state = viewModel
        .screenState
        .collectAsState()
        .value


    val calendarState = rememberSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {


        CategorySelector(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            categories = categories,
            selectedCategories = state.selectedCategory,
            onCategorySelect = { viewModel.selectCategory(it) },
            onCategoryUnselect = { viewModel.unselectCategory(it) }
        )

        WeatherInfoPanel(
            modifier = Modifier.fillMaxWidth(),
            location = state.location,
            weatherInfo = state.weatherInfo,
            onTrackLocationClick = {
                viewModel.getWeatherInfo()
            }
        )

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = state.timestamp.toString())


            IconButton(onClick = {
                calendarState.show()
            }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )

            }
        }
        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true
            ),
            selection = CalendarSelection.Date { date ->
                viewModel.setTimestamp(date)
            }
        )


        BasicTextField(
            value = state.description,
            onValueChange = viewModel::setDescription,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
        )

        Button(
            onClick = {
                if(viewModel.screenState.value.description.isNotEmpty()) {
                    viewModel.addNote()
                    navController.navigate(Screen.DiaryNotesList.route)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Add new Note"
            )
        }
    }



}