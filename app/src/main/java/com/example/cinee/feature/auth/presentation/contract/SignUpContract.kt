package com.example.cinee.feature.auth.presentation.contract

import com.example.cinee.navigation.model.Destination

interface SignUpContract {
    sealed interface UiState {
        data class Success(
            val name: String,
            val surname: String,
            val email: String,
            val password: String,
            val confirmPassword: String,
            val isInputEnabled: Boolean,
        ) : UiState

        object Loading : UiState
        data class Error(val message: String) : UiState
    }

    sealed interface UiAction {
        data class ChangeName(val name: String) : UiAction
        data class ChangeSurname(val surname: String) : UiAction
        data class ChangeEmail(val email: String) : UiAction
        data class ChangePassword(val password: String) : UiAction
        data class ChangeConfirmPassword(val confirmPassword: String) : UiAction
        object Submit : UiAction
    }

    sealed interface SideEffect {
        data class NavigateToLoginScreen(val destination: Destination) : SideEffect
    }
}