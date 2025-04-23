package com.example.cinee.feature.auth.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinee.datastore.model.UserAccount
import com.example.cinee.feature.auth.domain.bus.SocialSignInBus
import com.example.cinee.feature.auth.domain.usecase.EmailPasswordSignInUseCase
import com.example.cinee.feature.auth.domain.usecase.FacebookSignInUseCase
import com.example.cinee.feature.auth.domain.usecase.GoogleSignInUseCase
import com.example.cinee.feature.auth.domain.usecase.PhoneNumberSignInUseCase
import com.example.cinee.feature.auth.presentation.contract.SignInContract.SideEffect
import com.example.cinee.feature.auth.presentation.contract.SignInContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignInContract.UiState
import com.example.cinee.feature.auth.presentation.model.FieldType
import com.example.cinee.feature.auth.presentation.util.validateForm
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.SideEffectBuffer
import com.example.cinee.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
    @Inject constructor(
        @ApplicationContext private val context: Context,
        private val dataStore: DataStore<UserAccount>,
        private val googleSignInUseCase: GoogleSignInUseCase,
        private val facebookSignInUseCase: FacebookSignInUseCase,
        private val phoneNumberSignInUseCase: PhoneNumberSignInUseCase,
        private val emailPasswordSignInUseCase: EmailPasswordSignInUseCase,
        private val socialSignInBus: SocialSignInBus
    )
    :ViewModel(),MVI<UiState,UiAction,SideEffect> by mvi(initialUiState()) {


        init {
            resetState()
        }


    override fun onAction(uiAction: UiAction) {
        when(uiAction){
            is UiAction.ChangeEmail -> changeEmail(uiAction.email)
            is UiAction.ChangePassword -> changePassword(uiAction.password)
            is UiAction.Submit -> submit()
            is UiAction.SignInWithGoogle -> initiateSocialMediaSignIn("Google")
            is UiAction.SignInWithFacebook -> initiateSocialMediaSignIn("Facebook")
            is UiAction.ClearEmailError -> clearEmailError()
            is UiAction.ReturnToSignIn -> resetState()
        }
    }

    private fun initiateSocialMediaSignIn(
        socialMedia: String
    ) {
        viewModelScope.launch {
            when (socialMedia) {
                "Google" -> socialSignInBus.requestGoogleLogin()
                "Facebook" -> socialSignInBus.requestFacebookLogin()
                else -> throw IllegalArgumentException("Unknown social media: $socialMedia")
            }
        }
    }


    private fun changeEmail(email: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(email = email))
    }

    private fun changePassword(password: String) {
        val uiState = uiState.value as UiState.Success
        updateUiState(newUiState = uiState.copy(password = password))
    }

    private fun submit() {
        val currentState = uiState.value as UiState.Success
        val email = currentState.email
        val password = currentState.password
        val errors = validateForm(
            email = email,
        )

        if (errors.isEmpty()) {
            viewModelScope.launch {
                updateUiState(currentState.copy(
                    isInputEnabled = false
                ))
                updateUiState(UiState.Loading)
                emailPasswordSignInUseCase(
                    email = email,
                    password = password
                ).onSuccess {
                    resetState()
                    val toastEffect = SideEffect.ShowToast("Login successful")
                    val navigateEffect = SideEffect.NavigateToProfileScreen

                    // Also buffer in the global buffer
                    SideEffectBuffer.emit(toastEffect)
                    SideEffectBuffer.emit(navigateEffect)
                }.onFailure { error ->
                    val toastEffect = SideEffect.ShowToast("Login failed: ${error.message}")
                    emitSideEffect(toastEffect)
                    updateUiState(UiState.Error(error.message ?: "Login failed"))
            }


            }
        } else {
            updateUiState(currentState.copy(
                emailError = errors[FieldType.EMAIL],
            ))
        }
    }

    private fun signInWithGoogle(activityContext: Context) {
        viewModelScope.launch {
            disableInput()
            updateUiState(UiState.Loading)

            googleSignInUseCase(activityContext)
                .onSuccess {
                    resetState()

                    SideEffectBuffer.emit("Google Sign In Success")
                    SideEffectBuffer.emit(SideEffect.NavigateToProfileScreen)
                }
                .onFailure { error ->
                    val toastEffect = SideEffect.ShowToast("Google login failed: ${error.message}")
                    emitSideEffect(toastEffect)
                    updateUiState(UiState.Error(error.message ?: "Google login failed"))
                }
        }
    }

    fun startGoogleSignIn(activityContext: Context) {
        signInWithGoogle(activityContext)
    }

    private fun signInWithFacebook(accessToken: String) {
        viewModelScope.launch {
            disableInput()
            updateUiState(UiState.Loading)
           facebookSignInUseCase(accessToken)
                .onSuccess { userId ->
                    Log.d("FacebookLogin", "Success: $userId")
                    resetState()
                    val toastEffect = SideEffect.ShowToast("Facebook login successful")
                    val navigateEffect = SideEffect.NavigateToProfileScreen


                    // Also buffer in the global buffer
                    SideEffectBuffer.emit(toastEffect)
                    SideEffectBuffer.emit(navigateEffect)
                }
                .onFailure { error ->
                    val toastEffect = SideEffect.ShowToast("Facebook login failed: ${error.message}")
                    SideEffectBuffer.emit(toastEffect)
                    updateUiState(UiState.Error(error.message ?: "Facebook login failed"))
                }
        }
    }

    private fun disableInput() {
        val currentState = uiState.value as UiState.Success
        updateUiState(currentState.copy(isInputEnabled = false))
    }
    private fun resetState() {
        viewModelScope.launch {
            updateUiState(UiState.Success(
                email = "",
                password = "",
                isInputEnabled = true,
            ))
        }
    }
    // Public method to be called from UI
    fun handleFacebookToken(token: String) {
        signInWithFacebook(token)
    }

    fun handleFacebookSignInCancel() {
        viewModelScope.launch {
            updateUiState((uiState.value as? UiState.Success)?.copy(
                isInputEnabled = true
            ) ?: UiState.Success(
                email = "",
                password = "",
                isInputEnabled = true,
            ))
            emitSideEffect(SideEffect.ShowToast("Login canceled"))
        }
    }

    fun handleFacebookSignInError(errorMessage: String) {
        viewModelScope.launch {
            updateUiState(UiState.Error(errorMessage))
        }
    }

    private fun clearEmailError() {
        val currentState = uiState.value as UiState.Success
        updateUiState(currentState.copy(emailError = null))
    }



}

private fun initialUiState():UiState = UiState.Loading