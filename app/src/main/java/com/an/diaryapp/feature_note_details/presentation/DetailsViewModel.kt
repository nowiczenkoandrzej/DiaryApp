package com.an.diaryapp.feature_note_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.core.domain.repository.NotesRepository
import com.an.diaryapp.core.domain.model.NoteItem
import com.an.diaryapp.core.domain.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: NotesRepository
): ViewModel() {

    private val _note = MutableStateFlow<NoteItem?>(null)
    val note = _note.asStateFlow()


    fun getNoteById(id: Long) {
        viewModelScope.launch {

            val note = repository.getNoteById(id)

            when(note) {
                is Resource.Error -> {

                }
                is Resource.Success -> _note.value = note.data
            }
        }
    }




}