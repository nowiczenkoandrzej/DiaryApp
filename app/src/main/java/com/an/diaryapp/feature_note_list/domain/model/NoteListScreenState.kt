package com.an.diaryapp.feature_note_list.domain.model

import com.an.diaryapp.core.domain.model.NoteItem
import java.time.LocalDate

data class NoteListScreenState(
    val notes: List<NoteItem> = emptyList(),
    val searchBarText: String = "",
    val isSearchBarActive: Boolean = false,
    val areFiltersActive: Boolean = false
)
