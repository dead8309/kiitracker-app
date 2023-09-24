package com.kiitracker.domain

import kotlinx.serialization.Serializable

@Serializable
data class DailySchedule(
    val courses: List<Course>,
    val day: String,
    val classRoom: String = ""
)