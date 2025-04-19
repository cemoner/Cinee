package com.example.cinee.component.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.cinee.navigation.model.BottomNavigationItem
import com.example.cinee.navigation.model.Destination

@Composable
fun CustomNavigationRail(
    items: List<BottomNavigationItem>,
    isLoggedIn:Boolean,
    checkHasRoute: (currentDestination: NavDestination?, destination: Destination) -> Boolean,
    currentDestination: NavDestination?,
    onSelectedItemNavigation: (destination: Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
    ) {
        items.forEach { item ->
            NavigationRailItem(
                icon = { Icon(item.icon, contentDescription = stringResource (item.label)) },
                label = { Text(stringResource(item.label)) },
                selected = checkHasRoute(currentDestination, item.destination),
                onClick = {
                    if (item.destination == Destination.ProfileGraph && !isLoggedIn) {
                        onSelectedItemNavigation(Destination.AuthenticationGraph)
                    } else {
                        onSelectedItemNavigation(item.destination)
                    }
                }
            )
        }
    }
}