package com.example.cinee.navigation

import androidx.navigation.NavHostController
import com.example.cinee.navigation.model.Destination


fun navigateTo(
    navController: NavHostController,
    popUpTo:Destination? = null,
    inclusive:Boolean = true,
    destination: Destination,
    saveState:Boolean = true,
    restoreState:Boolean = true,
    launchSingleTop:Boolean = true,

){
    navController.navigate(destination) {

        this.launchSingleTop = launchSingleTop

        popUpTo?.let {
            popUpTo(it) {
                this.saveState = saveState
                this.inclusive = inclusive
            }
        }

        this.restoreState = restoreState

    }
}

fun popBackStack(
    navController: NavHostController,
){
    navController.popBackStack()
}