// app/src/main/java/com/example/cinee/feature/auth/domain/exception/AuthCancelledException.kt
package com.example.cinee.feature.auth.domain

/**
 * Exception thrown when a user explicitly cancels an authentication flow.
 * This distinguishes intentional cancellation from actual errors.
 */
class AuthCancelledException(message: String = "Authentication was cancelled") : Exception(message)