package com.example.cinee.feature.profile.presentation.composable

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.feature.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navigateToSignInScreen: () -> Unit,
){
    val viewModel: ProfileViewModel = hiltViewModel()
}


@Composable
fun ProfileContent(){

}