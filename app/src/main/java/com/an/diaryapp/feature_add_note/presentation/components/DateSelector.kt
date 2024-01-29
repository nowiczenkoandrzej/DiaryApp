package com.an.diaryapp.feature_add_note.presentation.components

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(34)
@Composable
fun DateSelector(
    datePickerState: DatePickerState,
    selectedDate: LocalDate,
    isDatePickerVisible: Boolean,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    onIconClick: () -> Unit,
) {
    

    /*val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )*/


    /*val openDialog = remember { mutableStateOf(false) }*/

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = selectedDate.toString())
        
        IconButton(onClick = {
            onIconClick()
        }) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null
            )

        }
    }


    if(isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = {
                    var selectedDate: LocalDate? = null

                    datePickerState.selectedDateMillis?.let {
                        selectedDate = convertMillisToDate(datePickerState.selectedDateMillis!!)
                    }

                    if(selectedDate != null) {
                        onConfirm(selectedDate!!)
                    }

                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun convertMillisToDate(millis: Long): LocalDate {
    return LocalDateTime.ofEpochSecond(millis,0, ZoneOffset.UTC).toLocalDate()
}