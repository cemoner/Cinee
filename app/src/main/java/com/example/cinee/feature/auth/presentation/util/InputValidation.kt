package com.example.cinee.feature.auth.presentation.util

import com.example.cinee.feature.auth.presentation.model.FieldType

// Validate with email, password and confirm password
fun validateForm(
    email: String,
    password: String,
    confirmPassword: String
): Map<FieldType,
        String?> {
    val errors = mutableMapOf<FieldType, String?>()

    // Email validation
    if (email.isBlank()) {
        errors[FieldType.EMAIL] = "Email cannot be empty"
    } else if (!isValidEmail(email)) {
        errors[FieldType.EMAIL] = "Invalid email format"
    }

    // Password validation
    if (password.length < 8) {
        errors[FieldType.PASSWORD] = "Password must be at least 8 characters"
    } else if (!isValidPassword(password)) {
        errors[FieldType.PASSWORD] = "Password must contain at least one letter and one number"
    }

    // Confirm password validation
    if (password != confirmPassword) {
        errors[FieldType.CONFIRM_PASSWORD] = "Passwords do not match"
    }
    return errors
}


// Validate only Email
fun validateForm(
    email: String,
): Map<FieldType,
        String?> {
    val errors = mutableMapOf<FieldType, String?>()

    // Email validation
    if (email.isBlank()) {
        errors[FieldType.EMAIL] = "Email cannot be empty"
    } else if (!isValidEmail(email)) {
        errors[FieldType.EMAIL] = "Invalid email format"
    }
    return errors
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d).+\$"
    return password.matches(passwordPattern.toRegex())
}