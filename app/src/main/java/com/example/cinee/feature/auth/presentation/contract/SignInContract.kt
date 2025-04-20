package com.example.cinee.feature.auth.presentation.contract

import com.example.cinee.navigation.model.Destination

interface SignInContract {
    sealed interface UiState {
        data class Success(
            val email: String,
            val password: String,
            val rememberMe: Boolean,
            val isInputEnabled: Boolean
        ):UiState
        data class Error(val message: String):UiState
        object Loading : UiState
    }
    sealed interface UiAction {
        data class ChangeEmail(val email: String) : UiAction
        data class ChangePassword(val password: String) : UiAction
        data class ChangeRememberMe(val rememberMe: Boolean) : UiAction
        object Submit : UiAction
        object SignInWithGoogle : UiAction
        object SignInWithFacebook : UiAction
    }

    sealed interface SideEffect {
        data class NavigateToHomeScreen(val destination: Destination) : SideEffect
    }
}