package com.example.cinee.feature.auth.presentation.composable

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.feature.auth.presentation.viewmodel.SignUpViewModel
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cinee.mvi.CollectSideEffect
import com.example.cinee.mvi.unpack
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

    CollectSideEffect(sideEffect) {
        when (it) {
            is SideEffect.NavigateToLoginScreen -> navigateToSignInScreen()
        }
    }
}