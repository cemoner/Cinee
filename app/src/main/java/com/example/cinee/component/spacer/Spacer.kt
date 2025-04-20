package com.example.cinee.component.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ShortcutSpacer(padding: Dp){
    Spacer(modifier = Modifier.height(padding))
}