package com.example.cinee.feature.auth.presentation.contract



interface SignUpContract {
    sealed interface UiState {
        data class Success(
            val name: String,
            val email: String,
            val password: String,
            val confirmPassword: String,
            val emailError: String? = null,
            val passwordError: String? = null,
            val confirmPasswordError: String? = null,
            val isInputEnabled: Boolean,
        ) : UiState

        object Loading : UiState
        data class Error(val message: String) : UiState
    }

    sealed interface UiAction {
        data class ChangeName(val name: String) : UiAction
        data class ChangeEmail(val email: String) : UiAction
        data class ChangePassword(val password: String) : UiAction
        data class ChangeConfirmPassword(val confirmPassword: String) : UiAction
        object ClearEmailError : UiAction
        object ClearPasswordError : UiAction
        object ClearConfirmPasswordError : UiAction
        object Submit : UiAction
        object ReturnToSignUp : UiAction
    }

    sealed interface SideEffect {
        object NavigateToSignInScreen : SideEffect
        data class ShowToast(val message: String) : SideEffect
    }
}