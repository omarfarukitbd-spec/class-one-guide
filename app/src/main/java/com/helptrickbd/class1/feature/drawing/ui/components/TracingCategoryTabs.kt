package com.helptrickbd.class1.feature.drawing.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.feature.drawing.domain.model.TracingCategory

@Composable
fun TracingCategoryTabs(
    categories: List<TracingCategory>,
    selectedCategory: TracingCategory,
    onCategorySelected: (TracingCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == selectedCategory
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = category.displayName,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color(0xFFFFD54F) else Color(0xFFE2E8F0) // 100% crisp readable contrast
                    )
                },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = if (isSelected) 1.5.dp else 1.dp,
                    color = if (isSelected) Color(0xFFFFD54F) else Color(0xFF333E54)
                ),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF283349),
                    containerColor = Color(0xFF1E2536)
                )
            )
        }
    }
}
