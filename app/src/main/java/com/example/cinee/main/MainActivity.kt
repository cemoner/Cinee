package com.example.cinee.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinee.component.appbar.BackNavigationIcon
import com.example.cinee.component.appbar.CineeTopAppBar
import com.example.cinee.component.appbar.CineeTopAppBarDefaults
import com.example.cinee.component.navigation.CustomNavigationBar
import com.example.cinee.datastore.model.UserAccount
import com.example.cinee.datastore.serializer.UserAccountSerializer
import com.example.cinee.feature.auth.domain.bus.SocialSignInBus
import com.example.cinee.feature.auth.presentation.viewmodel.SignInViewModel
import com.example.cinee.navigation.createGraph
import com.example.cinee.navigation.model.BottomNavigationItem
import com.example.cinee.navigation.model.Destination
import com.example.cinee.navigation.navigateTo
import com.example.cinee.navigation.popBackStack
import com.example.cinee.ui.theme.CineeTheme
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


val Context.dataStore: DataStore<UserAccount> by dataStore(
    fileName = "user_account.pb",
    serializer = UserAccountSerializer,
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var callbackManager: CallbackManager
    private val viewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var socialSignInBus: SocialSignInBus

    override fun onCreate(savedInstanceState: Bundle?) {

        callbackManager = CallbackManager.Factory.create()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    socialSignInBus.facebookSignInRequested.collect {
                        LoginManager.getInstance().logInWithReadPermissions(
                            this@MainActivity,
                            listOf("email", "public_profile")
                        )
                    }
                }
                launch {
                    socialSignInBus.googleSignInRequested.collect {
                        viewModel.startGoogleSignIn(this@MainActivity)
                    }
                }
            }
        }

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    viewModel.handleFacebookToken(result.accessToken.token)
                }

                override fun onCancel() {
                    viewModel.handleFacebookSignInCancel()
                }

                override fun onError(error: FacebookException) {
                    viewModel.handleFacebookSignInError(error.message ?: "Facebook login failed")
                }
            })

        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                return@setKeepOnScreenCondition false
            }
        }
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

    // Use this instead of onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}

@Composable
fun AppScreen(modifier: Modifier = Modifier) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    Box(
        modifier =
            Modifier
                .systemBarsPadding()
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        AppContent(mainViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
                when {
                    currentDestination?.hierarchy?.any {it.hasRoute(Destination.Home::class) } == true -> {
                        CineeTopAppBar(
                            navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                    )
                                }
                            },
                            title = "Cinee",
                            actions = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings",
                                    )
                                }
                            }
                        )
                    }
                    checkHasRoute(currentDestination,Destination.SignIn) || checkHasRoute(currentDestination,Destination.Profile) -> {
                        // No Top Bar
                    }
                    else -> {
                        CineeTopAppBar(
                            navigationIcon = {
                                CineeTopAppBarDefaults.BackNavigationIcon(
                                    onClick = { popBackStack(navController) },
                                )
                            }
                        )
                    }
                }
            },
        bottomBar = {
            if(shouldShowBottomBar(currentDestination)){
                CustomNavigationBar(
                    items = BottomNavigationItem.entries,
                    isLoggedIn = isLoggedIn,
                    checkHasRoute = ::checkHasRoute,
                    currentDestination = currentDestination,
                    onSelectedItemNavigation = { destination ->
                        navigateTo(
                            navController = navController,
                            destination = destination
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController,
            startDestination = Destination.ProfileGraph,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(100)) },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        )
        {
                createGraph(navController)
        }
    }

}



private fun checkHasRoute(currentDestination:NavDestination?,destination: Destination):Boolean{
    return try {
        currentDestination?.hierarchy?.any { it.hasRoute(destination::class) } == true
    }catch (e:NullPointerException) {
        e.printStackTrace()
        return false
    }
}

private fun shouldShowBottomBar(currentDestination: NavDestination?): Boolean {
    val bottomBarDestinations = setOf(
        Destination.Home::class,
        Destination.Watchlist::class,
        Destination.Profile::class
    )

    return currentDestination?.hierarchy?.any { destination ->
        bottomBarDestinations.any { destination.hasRoute(it) }
    } == true
}