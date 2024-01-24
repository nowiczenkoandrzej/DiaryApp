package com.an.diaryapp.core.domain.model

const val NOTE_ID = "note_id"

sealed class Screen(val route: String) {
    object DiaryNotesList: Screen(route = "diary_notes_list")
    object AddNote: Screen(route = "add_note")
    object Settings: Screen(route = "settings")
    object NoteDetails: Screen(route = "note_details/{$NOTE_ID}") {
        fun passId(id: Long) = "note_details/$id"
    }
}