package com.an.diaryapp.feature_add_note.domain

object NoteHintTexts {

    private val hints = listOf(
        "Share your day's spark...",
        "What's up today?",
        "Happy moments welcome.",
        "Reflect, grow, repeat.",
        "Your day's story."
    )
    fun random() = hints.random()

}