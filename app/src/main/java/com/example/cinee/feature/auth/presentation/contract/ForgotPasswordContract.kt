package com.example.cinee.feature.auth.presentation.contract

interface ForgotPasswordContract {
    sealed class UiState {
        object Loading : UiState()
        data class Success(val email: String) : UiState()
        data class Error(val message: String) : UiState()
    }
    sealed class UiAction {
        data class ChangeEmail(val email: String) : UiAction()
        object Submit : UiAction()
    }
    sealed class SideEffect {
        object NavigateToSignInScreen : SideEffect()
    }
}