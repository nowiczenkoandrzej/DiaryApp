package com.an.diaryapp.feature_settings.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.an.diaryapp.R
import com.an.diaryapp.feature_settings.domain.model.SettingsScreenState

@Composable
fun NotificationSetter(
    modifier: Modifier,
    state: SettingsScreenState,
    onCheckBoxCheck: (isChecked: Boolean) -> Unit,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Switch(
                checked = state.isNotificationSwitchChecked,
                onCheckedChange =  { isChecked ->
                    onCheckBoxCheck(isChecked)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Daily Reminder")
        }
        AnimatedVisibility(visible =  state.isNotificationSwitchChecked) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Every Day at: ${state.notificationTime}")

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(onClick = { onButtonClick() }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_access_time_24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = "Set Hour")
                    }
                }
            }
        }

    }
}