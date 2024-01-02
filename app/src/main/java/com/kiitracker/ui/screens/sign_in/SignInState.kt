package com.kiitracker.ui.screens.sign_in

data class SignInState(
    val isLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val failedSignInAttempts: Int = 0
)