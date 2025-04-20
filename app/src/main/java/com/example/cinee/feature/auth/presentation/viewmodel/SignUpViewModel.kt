package com.example.cinee.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinee.feature.auth.presentation.contract.SignInContract
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

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.ChangeConfirmPassword -> TODO()
            is UiAction.ChangeEmail -> TODO()
            is UiAction.ChangeName -> TODO()
            is UiAction.ChangePassword -> TODO()
            is UiAction.ChangeSurname -> TODO()
            is UiAction.Submit -> TODO()
        }
    }

    fun changeName(name: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(name = name))
    }

    fun changeSurname(surname: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(surname = surname))
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