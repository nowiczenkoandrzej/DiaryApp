package com.an.diaryapp.feature_note_list.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.an.diaryapp.core.domain.model.Screen
import com.an.diaryapp.core.presentation.components.ListCategory
import com.an.diaryapp.feature_note_list.presentation.components.DeleteNoteDialog
import com.an.diaryapp.feature_note_list.presentation.components.NoteListItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NotesListScreen(
    viewModel: NotesListViewModel,
    navController: NavController
) {

    val notesList = viewModel
        .notesState
        .collectAsState()
        .value

    var showDeleteNoteDialog by remember {
        mutableStateOf(false)
    }

    var deletedNoteId by remember {
        mutableLongStateOf(0)
    }

    if(showDeleteNoteDialog) {
        DeleteNoteDialog(
            onDismissRequest = {
                showDeleteNoteDialog = false
                deletedNoteId = 0
            },
            onConfirmation = {
                viewModel.removeNote(deletedNoteId)
                showDeleteNoteDialog = false
                deletedNoteId = 0
            },
            dialogTitle = "Do you want to delete that note?",
            icon = Icons.Default.Delete
        )
    }


    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner
        .lifecycle
        .currentStateFlow
        .collectAsState()

    LaunchedEffect(lifecycleState) {
        when(lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {viewModel.getNotes()}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {viewModel.getNotes()}
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val grouped = notesList.groupBy { it.timestamp.month }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {

            grouped.forEach { month, notes ->
                stickyHeader {
                    ListCategory(
                        title = month.toString(),
                        modifier = Modifier
                    )
                }
                items(notes) { note ->
                    NoteListItem(
                        noteItem = note,
                        onClick =  {
                            navController.navigate(
                                Screen.NoteDetails.passId(note.id!!)
                            )
                        },
                        onLongClick = {
                            showDeleteNoteDialog = true
                            deletedNoteId = note.id!!
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

            }

        }


    }


}