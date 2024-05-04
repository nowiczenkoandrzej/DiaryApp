package com.an.diaryapp.feature_settings.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.an.diaryapp.R
import com.an.diaryapp.feature_notification.AlarmItem
import com.an.diaryapp.feature_settings.domain.model.SettingsScreenEvent
import com.an.diaryapp.feature_settings.presentation.components.LocationPicker
import com.an.diaryapp.feature_settings.presentation.components.NotificationSetter
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDateTime
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

        LocationPicker(
            modifier = Modifier.fillMaxWidth(),
            state = state
        )

    }



}