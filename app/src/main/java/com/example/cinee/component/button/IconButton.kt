package com.example.cinee.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CustomIconButton(
    onClick:() -> Unit,
    iconPainter: Painter
){
        IconButton(
            onClick = { onClick() },
            modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(50)).padding(2.dp)
        ) {
            Image(
                painter = iconPainter,
                contentDescription = "platform button",
                modifier = Modifier.size(36.dp)
            )
    }
}