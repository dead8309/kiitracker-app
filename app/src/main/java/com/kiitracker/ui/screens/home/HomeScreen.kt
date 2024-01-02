package com.kiitracker.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kiitracker.domain.models.Routine
import com.kiitracker.domain.models.UserData
import com.kiitracker.ui.components.SubjectCard
import com.kiitracker.ui.components.WeekDayTab
import com.kiitracker.ui.components.days
import com.kiitracker.ui.theme.AppTheme
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: UserData?,
    routine: Routine? = Routine(),
    onNavigateToSettings: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome ${user?.username} 👋",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onNavigateToSettings()
                    }) {
                        AsyncImage(
                            modifier = Modifier
                                    .size(52.dp)
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.secondaryContainer,
                                    CircleShape,
                                )
                                .clip(CircleShape),
                            model = user?.photoUrl,
                            contentDescription = "${user?.username}'s avatar"
                        )
                    }
                }
            )
        }
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
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
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
                    if (routine == null) {
                        return@LazyColumn
                    }
                    routine[days[it]].forEachIndexed { index, course ->
                        item(key = course.id) {
                            SubjectCard(
                                selected = selectedCourse == index,
                                onSelect = {
                                    selectedCourse =
                                        if (selectedCourse == index) -1
                                        else index
                                },
                                title = course.course,
                                timeSlot = course.timeSlot,
                                classRoom = course.classRoom,
                                campus = course.campus,
                            )
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
    val user = UserData(
        username = "John Doe",
        email = "",
        uid = "",
        photoUrl = ""
    )
    AppTheme {
        HomeScreen(user)
    }
}