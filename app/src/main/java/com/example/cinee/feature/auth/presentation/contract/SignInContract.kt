package com.example.cinee.feature.auth.presentation.contract


interface SignInContract {
    sealed interface UiState {
        data class Success(
            val email: String,
            val password: String,
            val emailError: String? = null,
            val isInputEnabled: Boolean,
        ):UiState
        data class Error(val message: String):UiState
        object Loading : UiState
    }
    sealed interface UiAction {
        data class ChangeEmail(val email: String) : UiAction
        data class ChangePassword(val password: String) : UiAction
        object ClearEmailError : UiAction
        object Submit : UiAction
        object SignInWithGoogle : UiAction
        object SignInWithFacebook : UiAction
        object ReturnToSignIn : UiAction
    }

    sealed interface SideEffect {
        object NavigateToProfileScreen : SideEffect
        data class ShowToast(val message: String) : SideEffect
    }
}