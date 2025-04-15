package com.example.cinee.navigation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cinee.R

sealed class BottomNavigationItem(
    val destination: Destination,
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int,
) {
    object Home : BottomNavigationItem(
        Destination.Home,
        R.string.home,
        Icons.Default.Home,
        R.string.home,
    )

    object Favorites : BottomNavigationItem(
        Destination.Favorites,
        R.string.favorites,
        Icons.Default.Favorite,
        R.string.favorites,
    )

    object Profile : BottomNavigationItem(
        Destination.Profile,
        R.string.profile,
        Icons.Default.Person,
        R.string.profile,
    )
}
