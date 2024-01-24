package com.an.diaryapp.feature_note_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.core.data.NotesRepositoryImpl
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: NotesRepositoryImpl
): ViewModel() {

    private val _notesState = MutableStateFlow<List<NoteItem>>(emptyList())
    val notesState = _notesState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    fun removeNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
            getNotes()
        }
    }

    fun getNotes(content: String = "") {
        viewModelScope.launch {
            when(val result = repository.getNotes(content)) {
                is Resource.Error -> {
                    _error.value = result.error
                    _notesState.value = emptyList()
                }
                is Resource.Success ->  {
                    _notesState.value = result.data ?: emptyList()
                    _error.value = null
                }
            }
        }

    }




}