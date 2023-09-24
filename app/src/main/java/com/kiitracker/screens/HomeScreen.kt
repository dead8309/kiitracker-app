package com.kiitracker.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kiitracker.domain.Course
import com.kiitracker.domain.DailySchedule
import com.kiitracker.ui.actions.HomeScreenActions
import com.kiitracker.ui.components.AppBar
import com.kiitracker.ui.components.SubjectCard
import com.kiitracker.ui.components.WeekDayTab
import com.kiitracker.ui.components.days
import com.kiitracker.ui.theme.AppTheme
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    topAppBar: @Composable () -> Unit = {},
    routine: List<DailySchedule> = emptyList()
) {
    Scaffold(
        topBar = topAppBar
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            var selectedDay by remember { mutableIntStateOf(if (dayOfWeek == 1) 6 else dayOfWeek - 2) }
            val pagerState = rememberPagerState {
                days.size
            }
            LaunchedEffect(selectedDay) {
                pagerState.animateScrollToPage(selectedDay)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress ) {
                if (!pagerState.isScrollInProgress) {
                    selectedDay = pagerState.currentPage
                }
            }
            WeekDayTab(
                selectedDay = selectedDay,
                onDaySelected = {
                    selectedDay = it
                }
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                var selectedCourse by remember {
                    mutableIntStateOf(-1)
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    routine.forEach { dailySchedule ->
                        if (dailySchedule.day == days[it]) {
                            dailySchedule.courses.forEachIndexed { index, course ->
                                item(key = "$course-${course.timeSlot.startTime}") {
                                    SubjectCard(
                                        selected = selectedCourse == index,
                                        onSelect = {
                                            selectedCourse =
                                                if (selectedCourse == index) -1
                                                else index
                                        },
                                        title = course.toString(),
                                        timeSlot = "${course.timeSlot.startTime} - ${course.timeSlot.endTime}",
                                        `class` = when (course) {
                                            is Course.Lab -> ""
                                            is Course.Elective -> course.classroom
                                            else -> dailySchedule.classRoom
                                        },
                                        campus = course.campus,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            topAppBar = {
                AppBar(
                    title = "Schedule",
                    actions = {
                        HomeScreenActions()
                    }
                )
            }
        )
    }
}