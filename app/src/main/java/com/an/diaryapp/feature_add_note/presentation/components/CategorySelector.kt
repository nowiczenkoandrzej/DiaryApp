package com.an.diaryapp.feature_add_note.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.an.diaryapp.core.domain.model.Category
import com.an.diaryapp.core.presentation.components.CategoryItem
import java.lang.Long

@Composable
fun CategorySelector(
    categories: List<Category>,
    selectedCategories: List<Category>,
    onCategorySelect: (Category) -> Unit,
    onCategoryUnselect: (Category) -> Unit,
) {

    var isVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories:",
                modifier = Modifier
                    .clickable { isVisible = !isVisible }
            )
            LazyRow(
                modifier = Modifier.weight(1F)
            ) {
                items(selectedCategories) {
                    CategoryItem(category = it)
                }
            }

        }

        Spacer(modifier = Modifier.height(4.dp))
        AnimatedVisibility(visible = isVisible) {
            LazyColumn() {
                items(categories) { category ->

                    var isSelected by remember {
                        mutableStateOf(selectedCategories.contains(category))
                    }

                    val color = category.backgroundColor.substring(2)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(Long.parseLong(color, 16))
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = category.name,
                                modifier = Modifier.padding(4.dp)
                            )
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = {
                                    if(isSelected)
                                        onCategoryUnselect(category)
                                    else
                                        onCategorySelect(category)

                                    isSelected = !isSelected
                                }
                            )
                        }

                    }
                }
            }
        }
    }




}