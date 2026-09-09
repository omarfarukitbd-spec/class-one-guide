package com.helptrickbd.class1.feature.learn_hub.ui.quiz.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuizOptionGrid(
    options: List<String>,
    selectedOption: String?,
    isAnswered: Boolean,
    targetLetter: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        val row1 = options.take(2)
        val row2 = options.drop(2).take(2)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            row1.forEach { option ->
                QuizOptionCard(
                    letter = option,
                    isSelected = selectedOption == option,
                    isAnswered = isAnswered,
                    isCorrectTarget = option == targetLetter,
                    onClick = { onOptionSelected(option) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (row2.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                row2.forEach { option ->
                    QuizOptionCard(
                        letter = option,
                        isSelected = selectedOption == option,
                        isAnswered = isAnswered,
                        isCorrectTarget = option == targetLetter,
                        onClick = { onOptionSelected(option) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(name = "Quiz Option Grid")
@Composable
private fun QuizOptionGridPreview() {
    com.helptrickbd.class1.core.designsystem.theme.AppTheme {
        QuizOptionGrid(
            options = listOf("অ", "আ", "ই", "ঈ"),
            selectedOption = null,
            isAnswered = false,
            targetLetter = "অ",
            onOptionSelected = {}
        )
    }
}
