package com.an.diaryapp.feature_note_list.domain.model

import java.time.LocalDate

sealed class NoteListEvent{
    object GetNotes: NoteListEvent()
    data class RemoveNote(val noteId: Long): NoteListEvent()
    data class TypeSearchBar(val text: String): NoteListEvent()
    data class GetFilteredNotes(val filterType: FilterType): NoteListEvent()
    object ActiveSearchBar: NoteListEvent()
}
