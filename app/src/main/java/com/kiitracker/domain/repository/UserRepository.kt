package com.kiitracker.domain.repository

import com.kiitracker.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserRoutineFlow(): Flow<User?>
}