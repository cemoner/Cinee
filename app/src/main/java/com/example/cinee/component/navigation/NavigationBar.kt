package com.example.cinee.component.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavDestination
import com.example.cinee.navigation.model.BottomNavigationItem
import com.example.cinee.navigation.model.Destination
import com.example.cinee.ui.theme.CineeTheme



@Composable
fun CustomNavigationBar(
    items: List<BottomNavigationItem>,
    isLoggedIn:Boolean,
    checkHasRoute: (currentDestination: NavDestination?, destination: Destination) -> Boolean,
    currentDestination: NavDestination?,
    onSelectedItemNavigation: (destination:Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            NavigationBarItem(
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

@Preview(name = "Navigation Bar", showBackground = true)
@PreviewLightDark
@Composable
fun CustomNavigationBarPreview() {
    CineeTheme {
        var selectedItemIndex by remember { mutableStateOf(0) }
    }
}

