package com.example.cinee.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinee.feature.auth.domain.usecase.EmailPasswordSignUpUseCase
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cinee.feature.auth.presentation.model.FieldType
import com.example.cinee.feature.auth.presentation.util.validateForm
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val emailPasswordSignUpUseCase: EmailPasswordSignUpUseCase
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
            is UiAction.ReturnToSignUp -> resetState()
        }
    }

    private fun changeName(name: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(name = name))
    }

    private fun changeEmail(email: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(email = email))
    }

    private fun changePassword(password: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(password = password))
    }

    private fun changeConfirmPassword(confirmPassword: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(confirmPassword = confirmPassword))
    }

    private fun submit() {
        val currentState = uiState.value as UiState.Success

        val email = currentState.email
        val password = currentState.password
        val confirmPassword = currentState.confirmPassword

        val errors = validateForm(
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )

        if (errors.isEmpty()) {
            disableInput()
            updateUiState(UiState.Loading)
            viewModelScope.launch {
                emailPasswordSignUpUseCase(
                    name = currentState.name,
                    email = currentState.email,
                    password = currentState.password
                ).onSuccess {
                    resetState()
                    emitSideEffect(SideEffect.NavigateToSignInScreen)
                }.onFailure {
                    updateUiState(UiState.Error(it.message ?: "Unknown error"))
                    emitSideEffect(SideEffect.ShowToast(it.message ?: "Unknown error"))
                }
            }
        } else {
            updateUiState(currentState.copy(
                emailError = errors[FieldType.EMAIL],
                passwordError = errors[FieldType.PASSWORD],
                confirmPasswordError = errors[FieldType.CONFIRM_PASSWORD]
            ))
        }
    }


    private fun disableInput() {
        val currentState = uiState.value as UiState.Success
        updateUiState(currentState.copy(isInputEnabled = false))
    }
    private fun resetState() {
        viewModelScope.launch {
            updateUiState(
                UiState.Success(
                name = "",
                email = "",
                password = "",
                confirmPassword = "",
                isInputEnabled = true,
            ))
        }
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