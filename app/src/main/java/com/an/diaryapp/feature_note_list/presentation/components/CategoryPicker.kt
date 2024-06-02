package com.an.diaryapp.feature_note_list.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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



    var selectedCategory by remember {
        mutableStateOf("")
    }

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
                    selected = selectedCategory.equals(it.name),
                    onClick = {
                        Log.d("TAG", "CategoryPicker: $selectedCategory, ${it.name}, ${selectedCategory.equals(it.name)}")
                        selectedCategory = it.name
                        onSelect(it.name)
                    })
            }
        }

    }

}