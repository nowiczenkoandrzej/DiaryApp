package com.an.diaryapp.feature_note_list.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.presentation.components.CategoryItem

@Composable
fun CategoryPicker(
    modifier: Modifier,
    categories: List<Category>,
    onSelect: (String) -> Unit
) {

    var selectedCategory: String? = null

    LazyRow(
        modifier = modifier
    ) {
        items(categories) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    selectedCategory = it.name
                    onSelect(it.name)
                }
            ) {
                CategoryItem(category = it)
                RadioButton(
                    selected = selectedCategory == it.name,
                    onClick = {
                        selectedCategory = it.name
                        onSelect(it.name)
                    })
            }
        }

    }

}