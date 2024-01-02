package com.kiitracker.domain.interfaces

import android.content.Context
import com.kiitracker.domain.models.SignInResult
import com.kiitracker.domain.models.UserData

interface Auth {
    val currentUser: UserData?
    suspend fun login(context: Context): SignInResult
    suspend fun logout()
}