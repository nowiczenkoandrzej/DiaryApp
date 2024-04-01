package com.an.diaryapp.feature_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.an.diaryapp.feature_settings.data.AlarmSchedulerImpl
import com.an.diaryapp.feature_settings.domain.AlarmItem
import java.time.LocalDateTime

@Composable
fun SettingsScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val scheduler = AlarmSchedulerImpl(context)

    var alarmItem: AlarmItem? = null

    var seconds by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = seconds,
            onValueChange = {
                seconds = it
            }
        )
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            }
        )

        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = {
                alarmItem = AlarmItem(
                    time = LocalDateTime.now()
                        .plusSeconds(seconds.toLong()),
                    message = message
                )
                alarmItem?.let(scheduler::schedule)
                seconds = ""
                message = ""
            }) {
                Text(text = "Schedule")
            }

            Button(onClick = {
                alarmItem?.let(scheduler::cancel)
            }) {
                Text(text = "Cancel")
            }
        }
    }


}