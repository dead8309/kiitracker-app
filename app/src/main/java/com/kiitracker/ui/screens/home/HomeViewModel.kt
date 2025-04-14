package com.kiitracker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiitracker.core.data.KiitrackerPreferencesDataSource
import com.kiitracker.domain.models.Routine
import com.kiitracker.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState {
    data class Success(val routine: Routine) : HomeUiState
    data object Loading : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesDataSource: KiitrackerPreferencesDataSource
) : ViewModel() {
    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val state = _state.asStateFlow()

    init {
        loadUserRoutine()
    }

    private fun loadUserRoutine() {
        viewModelScope.launch {
            combine(
                userRepository.getUserRoutineFlow(),
                preferencesDataSource.userPreferences
            ) { userResult, prefs ->
                if (userResult?.routine == null) {
                    return@combine HomeUiState.Error("Routine data not found")
                }
                val routine = userResult.routine
                val displayRoutine = routine.copy(
                    saturday = when (prefs.saturdayRoutineDay) {
                        "monday" -> routine.monday
                        "tuesday" -> routine.tuesday
                        "wednesday" -> routine.wednesday
                        "thursday" -> routine.thursday
                        "friday" -> routine.friday
                        "saturday" -> routine.saturday
                        // Handle "No Class"
                        else -> emptyList()
                    }
                )
                HomeUiState.Success(routine = displayRoutine)
            }.catch { e ->
                _state.value = HomeUiState.Error(e.message ?: "An unknown error occurred")
            }.collect { combinedState ->
                _state.value = combinedState
            }
        }
    }
}