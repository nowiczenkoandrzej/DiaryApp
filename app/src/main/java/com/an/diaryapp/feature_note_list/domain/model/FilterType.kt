package com.an.diaryapp.feature_note_list.domain.model

import java.time.LocalDate

data class FilterType(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val category: String? = null,
)
