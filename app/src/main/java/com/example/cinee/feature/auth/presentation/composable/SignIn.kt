package com.example.cinee.feature.auth.presentation.composable

import androidx.compose.runtime.Composable
import com.example.cinee.component.text.HeaderText

@Composable
fun SignInScreen(
    navigateToSignUpScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit
){
    HeaderText("HELLO")
}


@Composable
fun SignInContent(){
    
}