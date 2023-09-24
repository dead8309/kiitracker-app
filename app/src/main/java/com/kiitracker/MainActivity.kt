package com.kiitracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kiitracker.data.A16Routine
import com.kiitracker.screens.CourseForm
import com.kiitracker.screens.HomeScreen
import com.kiitracker.ui.actions.HomeScreenActions
import com.kiitracker.ui.components.AppBar
import com.kiitracker.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                HomeScreen(
                    topAppBar = {
                        AppBar(
                            title = "Schedule",
                            actions = {
                                HomeScreenActions()
                            }
                        )
                    },
                    routine = A16Routine.weeklyRoutine
                )
            }
        }
    }
}