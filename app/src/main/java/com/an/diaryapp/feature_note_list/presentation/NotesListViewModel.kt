package com.an.diaryapp.feature_note_list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.domain.model.Resource
import com.an.diaryapp.core.domain.repository.NotesRepository
import com.an.diaryapp.feature_note_list.domain.model.FilterType
import com.an.diaryapp.feature_note_list.domain.model.NoteListEvent
import com.an.diaryapp.feature_note_list.domain.model.NoteListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow(NoteListScreenState())
    val screenState = _screenState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _filterType = MutableStateFlow(FilterType())
    val filterType = _filterType.asStateFlow()

    private var searchJob: Job? = null


    init {
        viewModelScope.launch {

            when(val result = notesRepository.getAllCategories()) {
                is Resource.Error -> {
                    _categories.value = emptyList()
                }
                is Resource.Success -> {
                    _categories.value = result.data ?: emptyList()
                }
            }

        }

    }

    fun onEvent(event: NoteListEvent) {
        when(event) {
            is NoteListEvent.GetNotes -> {
                viewModelScope.launch {
                    getNotes()
                }
            }
            is NoteListEvent.RemoveNote -> {
                viewModelScope.launch {
                    notesRepository.deleteNote(event.noteId)
                    getNotes()
                }
            }
            is NoteListEvent.TypeSearchBar -> {
                _screenState.value = screenState.value.copy(
                    searchBarText = event.text
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    getNotes()
                }
            }
            NoteListEvent.ActiveSearchBar -> {
                _screenState.value = screenState.value.copy(
                    isSearchBarActive = !screenState.value.isSearchBarActive
                )
            }

            is NoteListEvent.GetFilteredNotes -> {
                viewModelScope.launch {
                    val filteredNotes = notesRepository.getFilteredNotes(event.filterType)

                    Log.d("TAG", "onEvent: ${filteredNotes.data}")

                    when(filteredNotes) {
                        is Resource.Error -> {}
                        is Resource.Success ->  {
                            _screenState.value = screenState.value.copy(
                                notes = filteredNotes.data!!
                            )
                        }
                    }


                }
            }
        }
    }

    private suspend fun getNotes() {
        val notes = notesRepository.getNotes()
        when(notes) {
            is Resource.Error -> {}
            is Resource.Success ->  {
                _screenState.value = screenState.value.copy(
                    notes = notes.data ?: emptyList()
                )

            }
        }
    }


}