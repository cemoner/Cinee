package com.example.cinee.component.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun ClickableText(
    normalText: String?,
    clickableText: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {

    Row(
        modifier = modifier
    ){
        if(normalText != null){
            Text(
                text = normalText,
                style = style,
                color = color
            )
        }

        if(normalText != null && clickableText != null){
            Spacer(modifier = Modifier.width(4.dp))
        }

        if(clickableText != null){
            Text(
                text = clickableText,
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .semantics { contentDescription = clickableText },
                style = style,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}