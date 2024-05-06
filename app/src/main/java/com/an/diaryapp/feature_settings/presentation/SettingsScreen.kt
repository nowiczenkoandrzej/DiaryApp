package com.an.diaryapp.feature_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.an.diaryapp.feature_settings.domain.model.SettingsScreenEvent
import com.an.diaryapp.feature_settings.presentation.components.LocationPicker
import com.an.diaryapp.feature_settings.presentation.components.NotificationSetter
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    
    val state = viewModel
        .screenState
        .collectAsState()
        .value


    val clockState = rememberSheetState()

    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            val time = LocalTime.of(
                hours,
                minutes
            )
            viewModel.onEvent(SettingsScreenEvent.Schedule(time))
        }
    )

    LaunchedEffect(state.isTimePickerDialogVisible) {
        if(state.isTimePickerDialogVisible) {
            clockState.show()
            viewModel.onEvent(SettingsScreenEvent.TimePickerDialogShown)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .verticalScroll(rememberScrollState())
    ) {

        NotificationSetter(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            state = state,
            onCheckBoxCheck = { isChecked ->
                if(!isChecked)
                    viewModel.onEvent(SettingsScreenEvent.Cancel)

                viewModel.onEvent(SettingsScreenEvent.CheckBoxChecked)
            },
            onButtonClick =  {
                viewModel.onEvent(SettingsScreenEvent.ShowTimePickerDialog)
            }
        )

        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .padding(4.dp)
        )

        LocationPicker(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onMapClick = { location ->
                viewModel.onEvent(SettingsScreenEvent.SelectLocation(location))
            },
            onSaveButtonClick = {

            }
        )

    }



}