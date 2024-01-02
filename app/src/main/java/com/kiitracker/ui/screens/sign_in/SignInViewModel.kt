package com.kiitracker.ui.screens.sign_in

import androidx.lifecycle.ViewModel
import com.kiitracker.domain.models.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()
    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetSignInState() {
        _state.update { SignInState() }
    }

    fun triggerLoadingState() {
        _state.update { it.copy(
            isLoading = !it.isLoading
        ) }
    }
}