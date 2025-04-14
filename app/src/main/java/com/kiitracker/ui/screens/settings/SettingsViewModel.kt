package com.kiitracker.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiitracker.core.data.KiitrackerPreferencesDataSource
import com.kiitracker.domain.interfaces.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val isLoading: Boolean = true,
    val saturdayPreference: String = "saturday",
    val error: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auth: Auth,
    private val preferencesDataSource: KiitrackerPreferencesDataSource
) : ViewModel() {

    val state: StateFlow<SettingsUiState> = preferencesDataSource.userPreferences
        .map { domainPrefs ->
            SettingsUiState(
                isLoading = false,
                saturdayPreference = domainPrefs.saturdayRoutineDay
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState(isLoading = true)
        )


    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            auth.logout()
            onLogoutComplete()
        }
    }

    fun setSaturdayPreference(day: String) {
        viewModelScope.launch {
            preferencesDataSource.setSaturdayRoutineDay(day)
        }
    }
}