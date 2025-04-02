package com.example.cinee.component.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit) = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface,
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun CineeTopAppBarDefaults.BackNavigationIcon(
    onClick: () -> Unit,
    contentDescription: String? = "Navigate back",
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun CineeTopAppBarDefaults.MenuNavigationIcon(
    onClick: () -> Unit,
    contentDescription: String? = "Open menu",
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = contentDescription,
        )
    }
}

object CineeTopAppBarDefaults {
    val DefaultPadding = 16.dp
    val DefaultIconSize = 24.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun CineeTopAppBarPreview() {
    MaterialTheme {
        Column {
            // Basic TopAppBar
            CineeTopAppBar(
                title = "Basic TopAppBar"
            )

            // TopAppBar with back navigation
            CineeTopAppBar(
                title = "With Back Navigation",
                navigationIcon = {
                    CineeTopAppBarDefaults.BackNavigationIcon(
                        onClick = {}
                    )
                }
            )

            // TopAppBar with menu navigation
            CineeTopAppBar(
                title = "With Menu Navigation",
                navigationIcon = {
                    CineeTopAppBarDefaults.MenuNavigationIcon(
                        onClick = {}
                    )
                }
            )

            // TopAppBar with actions
            CineeTopAppBar(
                title = "With Actions",
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                }
            )

            // TopAppBar with custom colors
            CineeTopAppBar(
                title = "Custom Colors",
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    }
}

