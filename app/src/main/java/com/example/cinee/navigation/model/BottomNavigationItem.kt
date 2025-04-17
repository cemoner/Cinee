package com.example.cinee.navigation.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cinee.R

enum class BottomNavigationDestinations(
    val destination: Destination,
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int
) {
    HOME(Destination.HomeGraph,R.string.home, Icons.Default.Home, R.string.home),
    WATCHLIST(Destination.WatchlistGraph,R.string.watchlist, Icons.Default.WatchLater, R.string.watchlist),
    PROFILE(Destination.ProfileGraph,R.string.profile, Icons.Default.AccountBox, R.string.profile),
}