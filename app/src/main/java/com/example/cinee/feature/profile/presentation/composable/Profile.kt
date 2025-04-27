package com.example.cinee.feature.profile.presentation.composable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.component.appbar.BackNavigationIcon
import com.example.cinee.component.appbar.CineeTopAppBar
import com.example.cinee.component.appbar.CineeTopAppBarDefaults
import com.example.cinee.component.state.ErrorContent
import com.example.cinee.component.state.LoadingContent
import com.example.cinee.feature.profile.presentation.contract.DisplayState
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.SideEffect
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiAction
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiState
import com.example.cinee.feature.profile.presentation.viewmodel.ProfileViewModel
import com.example.cinee.mvi.unpack
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    navigateToSignInScreen: () -> Unit,
){
    val viewModel: ProfileViewModel = hiltViewModel()
    val (uiState,onAction,sideEffect) = viewModel.unpack()
    ProfileContent(
        uiState = uiState,
        onAction = onAction,
        sideEffect = sideEffect,
        navigateToSignInScreen = navigateToSignInScreen,
    )
}


@Composable
fun ProfileContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    navigateToSignInScreen: () -> Unit,

){
    when(uiState){
        is UiState.Loading -> LoadingContent(
            message = "Loading Profile...",
        )
        is UiState.Error -> ErrorContent(uiState.message, onRetry = {onAction(UiAction.SignOut)})
        is UiState.Success -> {
            Scaffold(
                topBar = {
                    TopBar(
                        uiState.displayState,
                        changeDisplayState = { onAction(UiAction.ChangeDisplayState) },
                        saveChanges = { onAction(UiAction.SaveChanges) }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ){innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .scrollable(
                            rememberScrollState(),
                            orientation = Orientation.Vertical
                        ),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    displayState: DisplayState,
    changeDisplayState: () -> Unit,
    saveChanges: () -> Unit,
) {

    // Render a single TopAppBar with conditional parameters
    CineeTopAppBar(
        title = when(
            displayState
        ){
            is DisplayState.View -> "Profile"
            is DisplayState.Edit -> "Edit Profile"
        },
        navigationIcon =
            {
                when(displayState){
                    is DisplayState.View -> {}
                    is DisplayState.Edit -> {
                        CineeTopAppBarDefaults.BackNavigationIcon(
                            onClick = changeDisplayState,
                            contentDescription = "Cancel Edit"
                        )
                    }
                }
            },
        actions = {
            IconButton(
                when(displayState){
                    is DisplayState.View -> changeDisplayState
                    is DisplayState.Edit -> saveChanges
                }
            ) {
                Icon(
                    imageVector = when(displayState){
                        is DisplayState.View -> Icons.Filled.Edit
                        is DisplayState.Edit -> Icons.Filled.Check
                    },
                    contentDescription = when(displayState){
                        is DisplayState.View -> "Edit Profile"
                        is DisplayState.Edit -> "Save Changes"
                    }
                )
            }
        }
    )
}