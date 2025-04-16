package com.example.cinee.navigation.model

import kotlinx.serialization.Serializable

sealed class Destination {
    // Home
    @Serializable
    object HomeGraph:Destination()

    @Serializable
    object Home : Destination()

    @Serializable
    data class MovieDetails(
        val movieId: Int,
    ) : Destination()

    // Watchlist

    @Serializable
    object WatchlistGraph : Destination()

    @Serializable
    object Watchlist : Destination()

    // Profile

    @Serializable
    object ProfileGraph : Destination()

    @Serializable
    object Profile : Destination()

    // Authentication

    @Serializable
    object AuthenticationGraph : Destination()

    @Serializable
    object SignIn : Destination()

    @Serializable
    object SignUp : Destination()

    @Serializable
    object ForgotPassword : Destination()
}
