package com.example.cinee.datastore.serializer

import androidx.datastore.core.Serializer
import com.example.cinee.datastore.model.UserAccount
import com.example.cinee.datastore.model.UserPreferences
import com.example.cinee.datastore.model.UserProfile
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserAccountSerializer: Serializer<UserAccount> {

    override val defaultValue: UserAccount
        get() = UserAccount(
            userProfile = UserProfile(
                userId = -1,
                name = "John",
                surName = "Smith",
                imageUrl = ""
            ),
            userPreferences = UserPreferences()
        )

    override suspend fun readFrom(input: InputStream): UserAccount {
        return try {
            Json.decodeFromString(
                deserializer = UserAccount.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserAccount, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserAccount.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}