package com.example.cinee.datastore.model

import kotlinx.serialization.Serializable

@Serializable
enum class LanguageCode(private val value: String) {
    en("en"),
    tr("tr"),
    de("de")
}