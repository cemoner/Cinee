package com.example.cinee.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cinee.feature.auth.domain.usecase.SignOutUseCase
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiState
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiAction
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.SideEffect
import com.example.cinee.mvi.MVI
import com.example.cinee.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val signOutUseCase: SignOutUseCase)
    : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(initialUiState()) {

}

private fun initialUiState():UiState = UiState.Loading