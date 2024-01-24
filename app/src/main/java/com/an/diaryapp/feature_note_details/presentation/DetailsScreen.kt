package com.an.diaryapp.feature_note_details.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel
) {
    
    val note = viewModel
        .note
        .collectAsState()
        .value

    note?.let {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = note.timestamp.toString())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = note.description)
        }
    }
    


}