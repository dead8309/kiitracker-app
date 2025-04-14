package com.kiitracker.domain.models

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