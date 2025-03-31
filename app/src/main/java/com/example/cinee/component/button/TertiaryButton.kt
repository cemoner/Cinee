package com.example.cinee.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cinee.ui.theme.CineeTheme
import com.example.cinee.ui.theme.Dimens

@Composable
fun TertiaryButton(
    onClick: () -> Unit,
    text:String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(Dimens.paddingSmall)
        )
    }
}


// Preview
@Preview(name = "Tertiary Button", showBackground = true)
@PreviewLightDark
@Composable
fun TertiaryButtonPreview() {
    CineeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TertiaryButton(
                onClick = {},
                text = "Enabled",
            )
            TertiaryButton(
                onClick = {},
                text = "Disabled",
                enabled = false
            )
        }
    }
}