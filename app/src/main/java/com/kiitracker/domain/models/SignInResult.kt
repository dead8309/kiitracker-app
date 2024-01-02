package com.kiitracker.domain.models

import com.google.firebase.auth.FirebaseUser

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val username: String?,
    val uid: String,
    val email: String,
    val photoUrl: String?,
)

fun FirebaseUser?.toUserData(): UserData? {
    return this?.run {
        UserData(
            uid = uid,
            email = email ?: "",
            photoUrl = photoUrl?.toString(),
            username = displayName
        )
    }
}