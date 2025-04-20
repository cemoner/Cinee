package com.example.cinee.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiAction
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.SideEffect
import com.example.cinee.feature.auth.presentation.contract.SignUpContract.UiState
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
)
    : ViewModel(),MVI<UiState, UiAction,SideEffect> by mvi(initialUiState()) {
}

private fun initialUiState():UiState = UiState.Loading