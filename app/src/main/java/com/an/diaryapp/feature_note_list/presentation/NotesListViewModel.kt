package com.an.diaryapp.feature_note_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.feature_note_list.domain.model.NoteListEvent
import com.an.diaryapp.feature_note_list.domain.model.NoteListScreenState
import com.an.diaryapp.feature_note_list.domain.model.NoteListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val useCases: NoteListUseCases
): ViewModel() {

    private val _screenState = MutableStateFlow(NoteListScreenState())
    val screenState = _screenState.asStateFlow()


    fun onEvent(event: NoteListEvent) {
        when(event) {
            is NoteListEvent.GetNotes -> {
                viewModelScope.launch {
                    getNotes()
                }
            }
            is NoteListEvent.RemoveNote -> {
                viewModelScope.launch {
                    useCases.removeNote(id = event.noteId)
                    getNotes()
                }
            }
        }
    }


    private suspend fun getNotes() {
        _screenState.value = screenState.value.copy(
            notes = useCases.getNotes(screenState.value.searchBarText)
        )
    }




}