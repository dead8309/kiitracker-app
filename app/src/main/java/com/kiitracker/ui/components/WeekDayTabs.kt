package com.kiitracker.ui.components

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

@Composable
fun WeekDayTab(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedDay,
        edgePadding = 0.dp
    ) {
        days.forEachIndexed { index, s ->
            Tab(
                text = { Text(text = s) },
                selected = index == selectedDay,
                onClick = {
                    onDaySelected(index)
                }
            )
        }
    }
}