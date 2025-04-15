package com.example.cinee.navigation.model

import kotlinx.serialization.Serializable

sealed class Destination {
    // Home
    @Serializable
    object Home : Destination()

    @Serializable
    object Search : Destination()

    @Serializable
    data class MovieDetails(
        val movieId: Int,
    ) : Destination()

    // Favorites
    @Serializable
    object Favorites : Destination()

    // Authentication
    @Serializable
    object Profile : Destination()

    @Serializable
    object Login : Destination()

    @Serializable
    object Register : Destination()

    @Serializable
    object ForgotPassword : Destination()
}
