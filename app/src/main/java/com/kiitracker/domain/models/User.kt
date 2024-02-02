package com.kiitracker.domain.models

import com.kiitracker.data.local.Prefs
import java.util.Date

/**
 * Represents a user in the app.
 *
 * @property lastLogin The last time the user logged in.
 * @property routine The user's routine.
 */
data class User(
    val email: String,
    val lastLogin: Date,
    val routine: Routine,
) {
    /**
     * Default constructor is required by Firestore.
     */
    @Suppress("unused")
    constructor() : this("", Date(), Routine())
}

/**
 * Represents a user's routine.
 *
 * @property saturday The user's routine for Saturday.
 * @property tuesday The user's routine for Tuesday.
 * @property thursday The user's routine for Thursday.
 * @property wednesday The user's routine for Wednesday.
 * @property friday The user's routine for Friday.
 * @property monday The user's routine for Monday.
 */
data class Routine(
    val saturday: List<Course> = emptyList(),
    val tuesday: List<Course> = emptyList(),
    val thursday: List<Course> = emptyList(),
    val wednesday: List<Course> = emptyList(),
    val friday: List<Course> = emptyList(),
    val monday: List<Course> = emptyList(),
) {
    operator fun get(s: String): List<Course> {
        return when (s) {
            "saturday" -> {
                when (Prefs[Prefs.SATURDAY_KEY, "saturday"]) {
                    "saturday" -> saturday
                    "tuesday" -> tuesday
                    "thursday" -> thursday
                    "wednesday" -> wednesday
                    "friday" -> friday
                    "monday" -> monday
                    else -> emptyList()
                }
            }
            "tuesday" -> tuesday
            "thursday" -> thursday
            "wednesday" -> wednesday
            "friday" -> friday
            "monday" -> monday
            else -> emptyList()
        }
    }
}

/**
 * Represents a course.
 *
 * @property timeSlot The time slot of the course.
 * @property campus The campus of the course.
 * @property classRoom The class room of the course.
 * @property course The course name.
 * @property id The id of the course [AutoGenerated by server using Date.now()].
 * @property type The type of the course.
 */
data class Course(
    val timeSlot: String,
    val campus: String,
    val classRoom: String,
    val course: String,
    val id: Long,
    val type: String
) {
    /**
     * Default constructor is required by Firestore.
     */
    @Suppress("unused")
    constructor() : this("", "", "", "", 0, "")
}