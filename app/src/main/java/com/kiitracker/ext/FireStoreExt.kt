package com.kiitracker.ext

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T> DocumentReference.addSnapshotListenerFlow(
    dataType: Class<T>,
    initialSend: suspend (ProducerScope<T?>) -> Unit
): Flow<T?> = callbackFlow {
    initialSend(this)
    val listener = EventListener<DocumentSnapshot> { snapshot, exception ->
        if (exception != null) {
            cancel()
            return@EventListener
        }

        if (snapshot != null && snapshot.exists()) {
            val data = snapshot.toObject(dataType)
            trySend(data)
        } else {
            // TODO make it return a result to notify user to create the routine from the website
            trySend(null)
        }
    }

    val register = addSnapshotListener(listener)
    awaitClose { register.remove() }
}