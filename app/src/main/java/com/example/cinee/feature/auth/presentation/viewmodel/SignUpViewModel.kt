package com.example.cinee.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cinee.feature.auth.presentation.model.FieldType
import com.example.cinee.feature.auth.presentation.validateForm
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.mvi
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
            is UiAction.ClearEmailError -> clearEmailError()
            is UiAction.ClearPasswordError -> clearPasswordError()
            is UiAction.ClearConfirmPasswordError -> clearConfirmPasswordError()


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
        val currentState = uiState.value as UiState.Success
        val errors = validateForm(
            email = currentState.email,
            password = currentState.password,
            confirmPassword = currentState.confirmPassword
        )

        if (errors.isEmpty()) {
            // Proceed with registration
            emitSideEffect(SideEffect.NavigateToSignInScreen)
        } else {
            updateUiState(currentState.copy(
                emailError = errors[FieldType.EMAIL],
                passwordError = errors[FieldType.PASSWORD],
                confirmPasswordError = errors[FieldType.CONFIRM_PASSWORD]
            ))
        }
    }


    private fun emitSideEffect(effect: SideEffect) {
        viewModelScope.emitSideEffect(effect)
    }

    private fun clearEmailError() {
        val currentState = uiState.value as UiState.Success
        updateUiState(currentState.copy(emailError = null))
    }

    private fun clearPasswordError() {
        val currentState = uiState.value as UiState.Success
        updateUiState(currentState.copy(passwordError = null))
    }

    private fun clearConfirmPasswordError() {
        val currentState = uiState.value as UiState.Success
        updateUiState(currentState.copy(confirmPasswordError = null))
    }
}

private fun initialUiState():UiState = UiState.Loading