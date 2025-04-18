package com.example.cinee.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val languageCode:LanguageCode = LanguageCode.en,
    val theme:Theme = Theme.SYSTEM
)

