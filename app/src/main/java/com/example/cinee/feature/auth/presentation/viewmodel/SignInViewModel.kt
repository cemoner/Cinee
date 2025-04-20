package com.example.cinee.feature.auth.presentation.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinee.datastore.model.UserAccount
import com.example.cinee.mvi.MVI
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.cinee.mvi.mvi
import com.example.cinee.feature.auth.presentation.contract.SignInContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignInContract.UiState
import com.example.cinee.feature.auth.presentation.contract.SignInContract.SideEffect
import com.example.cinee.navigation.model.Destination
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
    @Inject constructor(
        private val dataStore: DataStore<UserAccount>,
    )
    :ViewModel(),MVI<UiState,UiAction,SideEffect> by mvi(initialUiState()) {


        init {
            updateUiState(
                newUiState = UiState.Success(
                    email = "",
                    password = "",
                    rememberMe = false,
                    isInputEnabled = true
                )
            )
        }


    override fun onAction(uiAction: UiAction) {
        when(uiAction){
            is UiAction.ChangeEmail -> changeEmail(uiAction.email)
            is UiAction.ChangePassword -> changePassword(uiAction.password)
            is UiAction.ChangeRememberMe -> changeRememberMe(uiAction.rememberMe)
            is UiAction.Submit -> submit()
            is UiAction.SignInWithGoogle -> signInWithGoogle()
            is UiAction.SignInWithFacebook -> signInWithFacebook()
        }
    }

    fun changeEmail(email: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(email = email))
    }

    fun changePassword(password: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(password = password))
    }

    fun changeRememberMe(rememberMe: Boolean) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(rememberMe = rememberMe))
    }

    fun submit() {
        emitSideEffect(SideEffect.NavigateToHomeScreen(destination = Destination.ProfileGraph))
    }

    fun emitSideEffect(effect: SideEffect) {
        viewModelScope.emitSideEffect(effect)
    }

    fun signInWithGoogle() {
    }

    fun signInWithFacebook() {
    }
}

private fun initialUiState():UiState = UiState.Loading