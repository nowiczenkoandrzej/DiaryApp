package com.an.diaryapp.feature_note_list.domain.model

sealed class NoteListEvent{
    object GetNotes: NoteListEvent()
    data class RemoveNote(val noteId: Long): NoteListEvent()
    data class TypeSearchBar(val text: String): NoteListEvent()
    object ActiveSearchBar: NoteListEvent()
}
