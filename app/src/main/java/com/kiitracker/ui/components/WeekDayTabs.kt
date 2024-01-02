package com.kiitracker.ui.components

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import java.util.Locale

val days = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")

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
                text = { Text(text = s.formatDay()) },
                selected = index == selectedDay,
                onClick = {
                    onDaySelected(index)
                }
            )
        }
    }
}

private fun String.formatDay(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
    }.take(3)
}
