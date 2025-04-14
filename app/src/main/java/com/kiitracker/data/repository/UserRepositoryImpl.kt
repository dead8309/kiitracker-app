package com.kiitracker.data.repository

import com.kiitracker.data.remote.UserRemoteDataSource
import com.kiitracker.domain.models.User
import com.kiitracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: UserRemoteDataSource
): UserRepository {
    override suspend fun getUserRoutineFlow(): Flow<User?> {
        return db.getCurrentUserRoutineFromFireStoreAndListenForChanges()
    }
}