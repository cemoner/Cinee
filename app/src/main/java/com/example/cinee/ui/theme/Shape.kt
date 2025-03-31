package com.example.cinee.ui.theme
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),  // e.g., small chips
    small = RoundedCornerShape(4.dp),      // e.g., buttons
    medium = RoundedCornerShape(8.dp),     // e.g., cards
    large = RoundedCornerShape(16.dp) ,     // e.g., modals
    extraLarge = RoundedCornerShape(32.dp)  // e.g., dialogs
)