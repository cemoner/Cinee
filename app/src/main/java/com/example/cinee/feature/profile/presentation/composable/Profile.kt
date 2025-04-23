package com.example.cinee.feature.profile.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.component.text.HeaderText
import com.example.cinee.feature.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navigateToSignInScreen: () -> Unit,
){
    val viewModel: ProfileViewModel = hiltViewModel()
    HeaderText("Profile")
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.signOut()
                navigateToSignInScreen()
            }
        ) { Text("Sign Out") }
    }
}


@Composable
fun ProfileContent(){

}