package com.example.cinee.feature.profile.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.component.appbar.BackNavigationIcon
import com.example.cinee.component.appbar.CineeTopAppBar
import com.example.cinee.component.appbar.CineeTopAppBarDefaults
import com.example.cinee.component.button.PrimaryButton
import com.example.cinee.component.image.AvatarImage
import com.example.cinee.component.image.AvatarSize
import com.example.cinee.component.spacer.ShortcutSpacer
import com.example.cinee.component.state.ErrorContent
import com.example.cinee.component.state.LoadingContent
import com.example.cinee.component.text.EditableTextField
import com.example.cinee.feature.profile.presentation.contract.DisplayState
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.SideEffect
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiAction
import com.example.cinee.feature.profile.presentation.contract.ProfileContract.UiState
import com.example.cinee.feature.profile.presentation.viewmodel.ProfileViewModel
import com.example.cinee.mvi.unpack
import com.example.cinee.ui.theme.Dimens
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
                        .verticalScroll(
                            rememberScrollState(),
                        ),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProfileView(
                        displayState = uiState.displayState,
                        name = uiState.userName,
                        email = uiState.email,
                        phoneNumber = uiState.phoneNumber,
                        profilePictureUrl = uiState.profilePictureUrl,
                        onAction = onAction
                    )
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


@Composable
fun ProfileView(
    displayState: DisplayState,
    name: String,
    email: String,
    phoneNumber: String, // Keep for potential future use
    profilePictureUrl: String?,
    onAction: (UiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Content should take available width
            .padding(Dimens.paddingLarge), // Add vertical padding
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Profile Header ---
        AvatarImage(
            imageUrl = null,
            size = AvatarSize.ExtraLarge, // Larger avatar for profile screen
            contentDescription = "Profile Picture",
            fallbackInitials = name.firstOrNull()?.toString()?.uppercase()
                    + name.split(" ").getOrNull(1)?.firstOrNull()?.toString()?.uppercase()
        )
        ShortcutSpacer(Dimens.paddingLarge)
        EditableTextField(
            text = name,
            onTextChange = { onAction(UiAction.UpdateName(it)) },
            isEditable = displayState is DisplayState.Edit,
        )
        EditableTextField(
            text = email,
            onTextChange = { onAction(UiAction.UpdateEmail(it)) },
            isEditable = displayState is DisplayState.Edit,
        )
        ShortcutSpacer(Dimens.paddingLarge)
        ProfileButton(
            label = "Watchlist",
            onClick = { /* TODO: onAction(UiAction.NavigateToWatchlist) */ },
            imageVector = Icons.AutoMirrored.Filled.List,
            enabled = displayState is DisplayState.View // Disable if in edit mode
        )
        ShortcutSpacer(Dimens.paddingMedium)
        ProfileButton(

            label = "Reset Password",
            onClick = { /* TODO: onAction(UiAction.NavigateToResetPassword) */ },
            imageVector = Icons.Filled.LockReset,
            enabled = displayState is DisplayState.View
        )
        ShortcutSpacer(Dimens.paddingMedium)
        ProfileButton(
            label = "Settings",
            onClick = { /* TODO: onAction(UiAction.NavigateToSettings) */ },
            imageVector = Icons.Filled.Settings,
            enabled = displayState is DisplayState.View
        )
        ShortcutSpacer(Dimens.paddingMedium)
        ProfileButton(

            label = "Notifications",
            onClick = { /* TODO: onAction(UiAction.NavigateToNotifications) */ },
            imageVector = Icons.Filled.NotificationsNone,
            enabled = displayState is DisplayState.View
        )
        ShortcutSpacer(Dimens.paddingMedium)
        ProfileButton(
            label = "Support",
            onClick = { /* TODO: onAction(UiAction.NavigateToSupport) */ },
            imageVector = Icons.Filled.SupportAgent,
            enabled = displayState is DisplayState.View
        )
        ShortcutSpacer(Dimens.paddingMedium)
        ProfileButton(
            // Using Notes or HelpOutline for FAQ
            label = "FAQ",
            onClick = { /* TODO: onAction(UiAction.NavigateToFaq) */ } ,
            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
            enabled = displayState is DisplayState.View
        )
        ShortcutSpacer(Dimens.paddingLarge)// Space before sign out
        ShortcutSpacer(Dimens.paddingLarge)
        // --- Sign Out Button ---
        PrimaryButton(
            onClick = { onAction(UiAction.SignOut) },
            text = "Sign Out",
            enabled = displayState is DisplayState.View
        )
    }
}

@Composable
fun ProfileButton(label:String,onClick:() -> Unit,imageVector: ImageVector, enabled:Boolean = true){
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow, disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        contentPadding = PaddingValues(vertical = Dimens.paddingMediumLarge, horizontal = Dimens.paddingExtraSmall)
    )
    {
        Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                imageVector = imageVector,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        }

    }
}