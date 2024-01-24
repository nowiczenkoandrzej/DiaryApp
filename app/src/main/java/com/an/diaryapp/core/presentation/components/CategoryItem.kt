package com.an.diaryapp.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.an.diaryapp.core.domain.model.Category
import java.lang.Long.parseLong


@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean = false
) {

    val color = category.backgroundColor.substring(2)

    Card(
        modifier = Modifier
            .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(parseLong(color, 16))
        )
    ) {
            Text(
                text = category.name,
                modifier = Modifier.padding(4.dp)
            )
    }

}