package com.example.cinee.datastore.model

import kotlinx.serialization.Serializable


@Serializable
data class UserAccount(
    val userProfile: UserProfile,
    val isLoggedIn:Boolean = false,
    val userPreferences: UserPreferences
)
