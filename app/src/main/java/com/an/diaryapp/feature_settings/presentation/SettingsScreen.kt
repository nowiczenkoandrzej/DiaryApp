package com.an.diaryapp.feature_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.an.diaryapp.feature_notification.AlarmItem
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

    val context = LocalContext.current
    //val scheduler = AlarmSchedulerImpl(context)


    var alarmItem: AlarmItem? = null
    
    
    val date = viewModel
        .date
        .collectAsState()
        .value

    val savedDate = viewModel
        .savedDate
        .collectAsState()
        .value

    var seconds by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    val clockState = rememberSheetState()
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            val time = LocalTime.of(
                hours,
                minutes
            )
            viewModel.setTime(time)

        }
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = seconds,
            onValueChange = {
                seconds = it
            }
        )
        
        Text(text = "Selected: ${date.hour}:${date.minute}")
        savedDate?.let {

            Text(text = "Saved: ${savedDate.hour}:${savedDate.minute}")

        }

        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = {
                alarmItem = AlarmItem(
                    time = LocalDateTime.now()
                        .plusSeconds(seconds.toLong()),
                    message = message
                )
                alarmItem?.let(viewModel::schedule)
                seconds = ""
                message = ""
            }) {
                Text(text = "Schedule")
            }

            Button(onClick = {
                viewModel.cancel()
            }) {
                Text(text = "Cancel")
            }
            Button(onClick = {
                clockState.show()
            }) {
                Text(text = "select date")
            }

            Button(onClick = {
                viewModel.saveTime()
            }) {
                Text(text = "save")
            }
        }
    }


}