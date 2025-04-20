package com.example.cinee.feature.auth.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.R
import com.example.cinee.component.button.CustomIconButton
import com.example.cinee.component.button.PrimaryButton
import com.example.cinee.component.input.PasswordTextField
import com.example.cinee.component.input.UsernameTextField
import com.example.cinee.component.text.BodyText
import com.example.cinee.component.text.ClickableText
import com.example.cinee.component.text.HeaderText
import com.example.cinee.feature.auth.presentation.contract.SignInContract.UiState
import com.example.cinee.feature.auth.presentation.contract.SignInContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignInContract.SideEffect
import com.example.cinee.feature.auth.presentation.viewmodel.SignInViewModel
import com.example.cinee.mvi.CollectSideEffect
import com.example.cinee.mvi.unpack
import com.example.cinee.ui.theme.Dimens
import kotlinx.coroutines.flow.Flow

@Composable
fun SignInScreen(
    navigateToSignUpScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit
){
    val viewModel:SignInViewModel = hiltViewModel()
    val (uiState,onAction,sideEffect) = viewModel.unpack()
    SignInContent(
        uiState,
        onAction,
        sideEffect,
        navigateToSignUpScreen,
        navigateToForgotPasswordScreen,
        navigateToProfileScreen
    )
}


@Composable
fun SignInContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    navigateToSignUpScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit
) {
    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.NavigateToHomeScreen -> navigateToProfileScreen()
        }
    }
    when(uiState){
        is UiState.Error -> {}
        is UiState.Loading -> {}
        is UiState.Success -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(Dimens.paddingLarge),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderText(
                    text = "Sign In",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                )

                ShortcutSpacer(Dimens.paddingLarge)
                BodyText(
                    text = "Sign in to use the full extent of the app",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
                ShortcutSpacer(Dimens.paddingLarge)
                UsernameTextField(
                    value = uiState.email,
                    onValueChange = { onAction(UiAction.ChangeEmail(it)) },
                    labelText = "Email",
                    supportingText = "Enter your email",
                    enabled = uiState.isInputEnabled,
                    isError = false,
                )
                ShortcutSpacer(Dimens.paddingMedium)
                PasswordTextField(
                    value = uiState.password,
                    onValueChange = { onAction(UiAction.ChangePassword(it)) },
                    labelText = "Password",
                    supportingText = "Enter your password",
                    enabled = uiState.isInputEnabled,
                    isError = false,
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ){
                    ClickableText(
                        normalText = null,
                        clickableText = "Forgot Password?",
                        onClick = navigateToForgotPasswordScreen,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                ShortcutSpacer(Dimens.paddingLarge)
                PrimaryButton(
                    text = "Sign In",
                    onClick = { onAction(UiAction.Submit) },
                    enabled = uiState.isInputEnabled,
                )
                ShortcutSpacer(Dimens.paddingLarge)
                ClickableText(
                    normalText = "Don't have an account?",
                    clickableText = "Sign up",
                    onClick = navigateToSignUpScreen,
                    style = MaterialTheme.typography.bodySmall,
                )
                ShortcutSpacer(Dimens.paddingLarge)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    BodyText("Or login with", style = MaterialTheme.typography.bodySmall)
                    ShortcutSpacer(Dimens.paddingMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        CustomIconButton(
                            onClick = { onAction(UiAction.SignInWithGoogle) },
                            iconPainter = painterResource(R.drawable.google_logo_icon)

                        )
                        CustomIconButton(
                            onClick = { onAction(UiAction.SignInWithFacebook) },
                            iconPainter = painterResource(R.drawable.facebook_logo_icon)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShortcutSpacer(padding:Dp){
    Spacer(modifier = Modifier.height(padding))
}
