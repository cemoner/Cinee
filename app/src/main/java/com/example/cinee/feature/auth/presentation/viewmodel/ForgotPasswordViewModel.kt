package com.example.cinee.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinee.feature.auth.presentation.contract.ForgotPasswordContract.UiState
import com.example.cinee.feature.auth.presentation.contract.ForgotPasswordContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.ForgotPasswordContract.SideEffect
import com.example.cinee.feature.auth.presentation.model.FieldType
import com.example.cinee.feature.auth.presentation.validateForm
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel
    @Inject constructor():ViewModel(), MVI<UiState, UiAction,SideEffect> by mvi(initialUiState()) {

        init {
            updateUiState(newUiState = UiState.Success(email = "", isInputEnabled = true, showDialog = false))
        }

    override fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.ChangeEmail -> changeEmail(uiAction.email)
            is UiAction.Submit -> submit()
            is UiAction.CloseDialog -> {closeDialog()}
            is UiAction.ClearEmailError -> clearEmailError()
        }
    }

    private fun changeEmail(email: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(email = email))
    }

    private fun submit() {
        val currentState = uiState.value as UiState.Success
        val errors = validateForm(
            email = currentState.email,
        )

        if (errors.isEmpty()) {
            // Proceed with registration
            updateUiState(newUiState = currentState.copy(
                showDialog = true,
                isInputEnabled = false
            ))
        } else {
            updateUiState(currentState.copy(
                emailError = errors[FieldType.EMAIL],
            ))
        }
    }
    private fun emitSideEffect(effect: SideEffect) {
        viewModelScope.emitSideEffect(effect)
    }

    private fun closeDialog() {
        updateUiState(newUiState = UiState.Success(email = "", isInputEnabled = true, showDialog = false))
        viewModelScope.launch {
            delay(500)
            emitSideEffect(SideEffect.NavigateToSignInScreen)
        }
    }
    private fun clearEmailError() {
        val currentState = uiState.value as UiState.Success
        updateUiState(currentState.copy(emailError = null))
    }
}

private fun initialUiState():UiState = UiState.Loading