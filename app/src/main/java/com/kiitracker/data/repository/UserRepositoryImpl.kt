package com.kiitracker.data.repository

import com.kiitracker.data.db.FireStoreDB
import com.kiitracker.domain.models.User
import com.kiitracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: FireStoreDB
): UserRepository {
    override suspend fun getUserRoutineFlow(): Flow<User?> {
        return db.getCurrentUserRoutineFromFireStoreAndListenForChanges()
    }
}