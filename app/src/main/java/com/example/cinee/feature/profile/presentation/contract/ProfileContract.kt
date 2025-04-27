package com.example.cinee.feature.profile.presentation.contract

interface ProfileContract {
    sealed interface UiState {
        object Loading : UiState
        data class Success(
            val userName: String,
            val email: String,
            val phoneNumber: String,
            val profilePictureUrl: String,
            val displayState:DisplayState = DisplayState.View
        ) : UiState
        data class Error(val message : String) : UiState
    }

    sealed interface UiAction {
        object SignOut : UiAction
        object ChangeDisplayState : UiAction
        object SaveChanges : UiAction
    }

    sealed interface SideEffect {
        object NavigateToSignInScreen : SideEffect
        data class ShowToast(val message: String) : SideEffect
    }
}

sealed interface DisplayState {
    data class Edit(
        val userName: String,
        val email: String,
        val phoneNumber: String,
        val profilePictureUrl: String
    ) : DisplayState
    object View : DisplayState
}