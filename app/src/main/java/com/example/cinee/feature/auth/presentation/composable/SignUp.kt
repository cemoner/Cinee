package com.example.cinee.feature.auth.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.component.button.PrimaryButton
import com.example.cinee.component.input.CustomTextField
import com.example.cinee.component.input.PasswordTextField
import com.example.cinee.component.spacer.ShortcutSpacer
import com.example.cinee.component.state.ErrorContent
import com.example.cinee.component.state.LoadingContent
import com.example.cinee.component.text.BodyText
import com.example.cinee.component.text.ClickableText
import com.example.cinee.component.text.HeaderText
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cinee.feature.auth.presentation.viewmodel.SignUpViewModel
import com.example.cinee.mvi.CollectSideEffect
import com.example.cinee.mvi.unpack
import com.example.cinee.ui.theme.Dimens
import kotlinx.coroutines.flow.Flow

@Composable
fun SignUpScreen(navigateToSignInScreen: () -> Unit) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val (uiState, onAction, sideEffect) = viewModel.unpack()
    SignUpContent(uiState, onAction, sideEffect, navigateToSignInScreen)
}


@Composable
fun SignUpContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    navigateToSignInScreen: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current


    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.NavigateToSignInScreen -> navigateToSignInScreen()
            is SideEffect.ShowToast ->  Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
    }

    when(uiState) {
        is UiState.Error -> ErrorContent(errorMessage = uiState.message, onRetry = {onAction(UiAction.ReturnToSignUp)}, buttonText = "Return to Sign Up")
        is UiState.Loading -> LoadingContent()
        is UiState.Success -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(Dimens.paddingLarge).scrollable(rememberScrollState(), orientation = Orientation.Vertical),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderText(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                )

                ShortcutSpacer(Dimens.paddingLarge)
                BodyText(
                    text = "Sign up to personalize your journey",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
                CustomTextField(
                    value = uiState.name,
                    onValueChange = { onAction(UiAction.ChangeName(it)) },
                    labelText = "Name",
                    supportingText = "Enter your full name",
                    enabled = uiState.isInputEnabled,
                )
                ShortcutSpacer(Dimens.paddingMedium)
                CustomTextField(
                    value = uiState.email,
                    onValueChange =
                        {
                        onAction(UiAction.ChangeEmail(it))
                            if (uiState.emailError != null) {
                                onAction(UiAction.ClearEmailError)
                            }
                        },
                    labelText = "Email",
                    supportingText = "Enter your email",
                    isError = uiState.emailError != null,
                    errorMessage = uiState.emailError
                )
                ShortcutSpacer(Dimens.paddingMedium)
                PasswordTextField(
                    value = uiState.password,
                    onValueChange =
                        {
                        onAction(UiAction.ChangePassword(it))
                            if (uiState.passwordError != null) {
                                onAction(UiAction.ClearPasswordError)
                            }
                        },
                    labelText = "Password",
                    supportingText = "Enter your password",
                    enabled = uiState.isInputEnabled,
                    isError = uiState.passwordError != null,
                    errorMessage = uiState.passwordError,
                )
                ShortcutSpacer(Dimens.paddingMedium)
                PasswordTextField(
                    value = uiState.confirmPassword,
                    onValueChange =
                        { onAction(UiAction.ChangeConfirmPassword(it))
                            if (uiState.passwordError != null) {
                                onAction(UiAction.ClearConfirmPasswordError) }
                        },
                    labelText = "Confirm Password",
                    supportingText = "Confirm your password",
                    enabled = uiState.isInputEnabled,
                    isError = uiState.confirmPasswordError != null,
                    errorMessage = uiState.confirmPasswordError
                )
                ShortcutSpacer(Dimens.paddingLarge)
                PrimaryButton(
                    text = "Sign In",
                    onClick = {
                        focusManager.clearFocus()
                        onAction(UiAction.Submit)
                              },
                    enabled = uiState.isInputEnabled,
                )
                ShortcutSpacer(Dimens.paddingLarge)
                ClickableText(
                    normalText = "Already have an account?",
                    clickableText = "Sign in",
                    onClick = navigateToSignInScreen,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
