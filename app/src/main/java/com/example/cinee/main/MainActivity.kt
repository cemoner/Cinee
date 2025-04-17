package com.example.cinee.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinee.component.appbar.BackNavigationIcon
import com.example.cinee.component.appbar.CineeTopAppBar
import com.example.cinee.component.appbar.CineeTopAppBarDefaults
import com.example.cinee.component.text.BodyText
import com.example.cinee.navigation.createGraph
import com.example.cinee.navigation.model.BottomNavigationDestinations
import com.example.cinee.navigation.model.Destination
import com.example.cinee.navigation.navigateTo
import com.example.cinee.ui.theme.CineeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface {
                        AppScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    Box(
        modifier =
            Modifier
                .systemBarsPadding()
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        AppContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination



    NavigationSuiteScaffold(
        navigationSuiteItems = {
            BottomNavigationDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = stringResource(it.contentDescription)
                        )
                    },
                    label = { BodyText( text = stringResource(it.label),style = MaterialTheme.typography.labelSmall) },
                    selected = currentDestination?.hierarchy?.any { it1-> it1.hasRoute(it.destination::class) } == true,
                    onClick = {
                        navigateTo(
                            navController = navController,
                            destination = it.destination
                        )
                    }
                )
            }
        },
        modifier = Modifier.fillMaxSize().systemBarsPadding()

    ){
        Scaffold(
            topBar = {
                when {
                    currentDestination?.hierarchy?.any {it.hasRoute(Destination.Home::class) } == true -> {
                        CineeTopAppBar(
                            "Cinee",
                            actions = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                    )
                                }
                            }
                        )
                    }
                    currentDestination?.hierarchy?.any {it.hasRoute(Destination.SignIn::class) || it.hasRoute(Destination.Profile::class)} == true -> {
                        // No Top Bar
                    }
                    else -> {
                        CineeTopAppBar(
                            navigationIcon = {
                                CineeTopAppBarDefaults.BackNavigationIcon(
                                    onClick = {},
                                )
                            }
                        )
                    }
                }
            }

        ) { innerPadding ->
            NavHost(navController = navController,
                startDestination = Destination.HomeGraph,
                modifier = Modifier.padding(innerPadding)
            )
            {
                createGraph(navController)
            }
        }
    }
}
