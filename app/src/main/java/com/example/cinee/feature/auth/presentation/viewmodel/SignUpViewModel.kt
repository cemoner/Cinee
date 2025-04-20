package com.example.cinee.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.mvi
import com.example.cinee.navigation.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
)
    : ViewModel(),MVI<UiState, UiAction,SideEffect> by mvi(initialUiState()) {

        init {
            updateUiState(
                newUiState = UiState.Success(
                    name = "",
                    email = "",
                    password = "",
                    confirmPassword = "",
                    isInputEnabled = true
                )
            )
        }
    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.ChangeName -> changeName(uiAction.name)
            is UiAction.ChangeEmail -> changeEmail(uiAction.email)
            is UiAction.ChangePassword -> changePassword(uiAction.password)
            is UiAction.ChangeConfirmPassword -> changeConfirmPassword(uiAction.confirmPassword)
            is UiAction.Submit -> submit()
        }
    }

    fun changeName(name: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(name = name))
    }

    fun changeEmail(email: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(email = email))
    }

    fun changePassword(password: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(password = password))
    }

    fun changeConfirmPassword(confirmPassword: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(confirmPassword = confirmPassword))
    }

    fun submit() {
        emitSideEffect(SideEffect.NavigateToLoginScreen(destination = Destination.ProfileGraph))
    }

    private fun emitSideEffect(effect: SideEffect) {
        viewModelScope.emitSideEffect(effect)
    }
}

private fun initialUiState():UiState = UiState.Loading