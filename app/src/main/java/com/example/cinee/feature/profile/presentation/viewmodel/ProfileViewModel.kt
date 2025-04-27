package com.example.cinee.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cinee.feature.auth.domain.usecase.SignOutUseCase
import com.example.cinee.feature.profile.presentation.contract.DisplayState
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiState
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiAction
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.SideEffect
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val signOutUseCase: SignOutUseCase)
    : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {


    init {
        updateUiState(UiState.Success(
            userName = "John Doe",
            email = "",
            phoneNumber = "1234567890",
            profilePictureUrl = "https://example.com/profile.jpg",
        ))
    }

    override fun onAction(uiAction: UiAction) {
        when(uiAction) {
            is UiAction.SignOut -> signOut()
            is UiAction.ChangeDisplayState -> changeDisplayState()
            is UiAction.SaveChanges -> saveChanges()
        }
    }

        private fun changeDisplayState() {
            val currentUiState = uiState.value as UiState.Success
            val newDisplayState = when (currentUiState.displayState) {
                is DisplayState.Edit -> DisplayState.View
                is DisplayState.View -> DisplayState.Edit(
                    userName = currentUiState.userName,
                    email = currentUiState.email,
                    phoneNumber = currentUiState.phoneNumber,
                    profilePictureUrl = currentUiState.profilePictureUrl
                )
            }
            updateUiState(currentUiState.copy(displayState = newDisplayState))
        }
    private fun signOut() {
    }

    private fun saveChanges() {
        val currentUiState = uiState.value as UiState.Success
        val newDisplayState = when (currentUiState.displayState) {
            is DisplayState.Edit -> DisplayState.View
            is DisplayState.View -> DisplayState.Edit(
                userName = currentUiState.userName,
                email = currentUiState.email,
                phoneNumber = currentUiState.phoneNumber,
                profilePictureUrl = currentUiState.profilePictureUrl
            )
        }
        updateUiState(currentUiState.copy(displayState = newDisplayState))
    }

}

private fun initialUiState():UiState = UiState.Loading