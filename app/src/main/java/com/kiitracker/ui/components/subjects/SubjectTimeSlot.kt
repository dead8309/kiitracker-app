package com.kiitracker.ui.components.subjects

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SubjectTimeSlot(timeSlot: String) {
    Text(
        text = timeSlot,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier
    )
}