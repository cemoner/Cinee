package com.example.cinee.component.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cinee.ui.theme.CineeTheme

data class NavigationItem(
    val icon: ImageVector,
    val label: String
)

@Composable
fun CustomNavigationBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) }
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
        
        val items = listOf(
            NavigationItem(Icons.Default.Home, "Home"),
            NavigationItem(Icons.Default.Home, "Search"),
            NavigationItem(Icons.Default.Home, "Profile")
        )

        CustomNavigationBar(
            items = items,
            selectedItemIndex = selectedItemIndex,
            onItemSelected = { selectedItemIndex = it }
        )
    }
}

