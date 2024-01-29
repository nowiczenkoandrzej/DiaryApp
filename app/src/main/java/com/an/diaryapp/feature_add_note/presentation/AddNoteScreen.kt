package com.an.diaryapp.feature_add_note.presentation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.an.diaryapp.feature_add_note.presentation.components.DateSelector
import com.an.diaryapp.feature_add_note.presentation.components.WeatherInfoPanel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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

    /*val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )*/

    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {


        CategorySelector(
            categories = categories,
            selectedCategories = state.selectedCategory,
            onCategorySelect = { viewModel.selectCategory(it) },
            onCategoryUnselect = { viewModel.unselectCategory(it) }
        )

        WeatherInfoPanel(
            location = state.location,
            weatherInfo = state.weatherInfo,
            onTrackLocationClick = {
                viewModel.getWeatherInfo()
            }
        )
        DateSelector(
            datePickerState = datePickerState,
            isDatePickerVisible = state.isDatePickerVisible,
            selectedDate = state.timestamp,
            onConfirm = { date ->
                viewModel.setTimestamp(date                    )
            },
            onDismiss = {
                viewModel.showOrHideDatePickerDialog()
            },
            onIconClick = {
                viewModel.showOrHideDatePickerDialog()
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