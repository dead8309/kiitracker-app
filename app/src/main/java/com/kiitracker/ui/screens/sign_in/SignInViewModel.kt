package com.kiitracker.ui.screens.sign_in

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.domain.models.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: Auth
): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun resetSignInState() {
        _state.update { SignInState() }
    }

    private fun triggerLoadingState() {
        _state.update { it.copy(
            isLoading = !it.isLoading
        )}
    }

    fun login(context: Context) {
        triggerLoadingState()
        viewModelScope.launch {
            val result = auth.login(context)
            _state.update { it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            ) }
            triggerLoadingState()
        }
    }
}