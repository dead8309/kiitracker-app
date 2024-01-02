package com.kiitracker.data.db

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.domain.models.User
import com.kiitracker.ext.addSnapshotListenerFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreDB @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: Auth
) {

    private suspend fun getRoutineFromCache(userId: String): User? {
        return try {
            val result = db.collection("users").document(userId).get(Source.CACHE).await()
            result.toObject<User>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getCurrentUserRoutineFromFireStoreAndListenForChanges(): Flow<User?> {
        val userId = auth.currentUser!!.uid
        val routine = db.collection("users").document(userId)
        return routine.addSnapshotListenerFlow(User::class.java, initialSend = {
            it.trySend(getRoutineFromCache(userId))
        })
    }
}

