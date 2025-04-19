package com.example.cinee.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.cinee.feature.auth.presentation.composable.ForgotPasswordScreen
import com.example.cinee.feature.auth.presentation.composable.SignInScreen
import com.example.cinee.feature.auth.presentation.composable.SignUpScreen
import com.example.cinee.feature.home.presentation.composable.HomeScreen
import com.example.cinee.feature.home.presentation.composable.MovieDetailsScreen
import com.example.cinee.feature.profile.presentation.composable.ProfileScreen
import com.example.cinee.feature.watchlist.presentation.composable.WatchlistScreen
import com.example.cinee.navigation.model.Destination

fun NavGraphBuilder.createGraph(
    navController: NavHostController,

){

    composable<Destination.Home> {
        HomeScreen(
            navigateToMovieDetailsScreen = { movieId ->
                navigateTo(
                    navController = navController,
                    destination = Destination.MovieDetails(movieId)
                )
            }
        )
    }
    composable<Destination.MovieDetails>{ backStackEntry ->
        val movie = backStackEntry.toRoute<Destination.MovieDetails>()
        MovieDetailsScreen(
            movie.movieId,
            navigateBack = { popBackStack(navController) }
        )
    }

    navigation<Destination.ProfileGraph>(startDestination = Destination.Profile){
        composable<Destination.Profile>{
            ProfileScreen(
                navigateToSignInScreen = {
                    navigateTo(
                        navController = navController,
                        destination = Destination.AuthenticationGraph,
                        popUpTo = Destination.ProfileGraph,
                        inclusive = true
                    )
                }
            )
        }
    }

    composable<Destination.Watchlist>{
        WatchlistScreen(
            onMovieClick = { movieId ->
                navigateTo(
                    navController = navController,
                    destination = Destination.MovieDetails(movieId))
            }
        )
    }

    navigation<Destination.AuthenticationGraph>(startDestination = Destination.SignIn){
        composable<Destination.SignIn>{
            SignInScreen(
                navigateToSignUpScreen = {
                    navigateTo(
                        navController = navController,
                        destination = Destination.SignUp
                    )
                    },

                navigateToForgotPasswordScreen = {
                    navigateTo(
                        navController = navController,
                        destination = Destination.ForgotPassword
                    )
                    },

                navigateToProfileScreen = {
                    navigateTo(
                        navController = navController,
                        destination = Destination.ProfileGraph,
                        popUpTo = Destination.AuthenticationGraph,
                        inclusive = true
                    )
                }
            )
        }
        composable<Destination.SignUp>{
            SignUpScreen(){
                navigateTo(
                    navController = navController,
                    destination = Destination.SignIn
                )
            }
        }

        composable<Destination.ForgotPassword>{
            ForgotPasswordScreen(){
                navigateTo(
                    navController = navController,
                    destination = Destination.SignIn
                )
            }
        }
    }
}