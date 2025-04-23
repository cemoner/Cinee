package com.example.cinee.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val userId:String,
    val name:String,
    val imageUrl:String,
)
