package com.example.cinee.feature.auth.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.component.button.PrimaryButton
import com.example.cinee.component.dialog.CustomAlertDialog
import com.example.cinee.component.input.CustomTextField
import com.example.cinee.component.spacer.ShortcutSpacer
import com.example.cinee.component.text.BodyText
import com.example.cinee.component.text.HeaderText
import com.example.cinee.feature.auth.presentation.contract.ForgotPasswordContract.UiState
import com.example.cinee.feature.auth.presentation.contract.ForgotPasswordContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.ForgotPasswordContract.SideEffect
import com.example.cinee.feature.auth.presentation.viewmodel.ForgotPasswordViewModel
import com.example.cinee.mvi.CollectSideEffect
import com.example.cinee.mvi.unpack
import com.example.cinee.ui.theme.Dimens
import kotlinx.coroutines.flow.Flow

@Composable
fun ForgotPasswordScreen(navigateToSignInScreen: () -> Unit) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    val (uiState, onAction, sideEffect) = viewModel.unpack()
    ForgotPasswordContent(
        uiState,
        onAction,
        sideEffect,
        navigateToSignInScreen)
}


@Composable
fun ForgotPasswordContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    navigateToSignInScreen: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.NavigateToSignInScreen -> navigateToSignInScreen()
        }
    }

    when(uiState) {
        is UiState.Error -> {}
        is UiState.Loading -> {}
        is UiState.Success -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(Dimens.paddingLarge),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderText(
                    text = "Forgot Password",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                ShortcutSpacer(Dimens.paddingLarge)
                BodyText(
                    text = "Enter your email address to receive a link to reset your password",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
                ShortcutSpacer(Dimens.paddingLarge)
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
                ShortcutSpacer(Dimens.paddingLarge)
                PrimaryButton(
                    text = "Submit",
                    onClick = {
                        focusManager.clearFocus()
                        onAction(UiAction.Submit)
                              },
                    enabled = uiState.isInputEnabled
                )
                CustomAlertDialog(
                    text = "If an account with that email exists, a password reset link has been sent. Check your email please.",
                    title = "Password Reset",
                    confirmText = "OK",
                    onConfirmation = { onAction(UiAction.CloseDialog) },
                    showDialog = uiState.showDialog,
                    onDismissRequest = {onAction(UiAction.CloseDialog)},
                    )
            }
        }
    }
}