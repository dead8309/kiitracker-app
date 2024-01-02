package com.kiitracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiitracker.domain.models.Routine
import com.kiitracker.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadUserRoutine()
    }

    private fun loadUserRoutine() {
        viewModelScope.launch {
            userRepository.getUserRoutineFlow()
                .collect {
                    _state.value = HomeState(routine = it?.routine)
                }
        }
    }
}

data class HomeState(
    val routine: Routine? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
