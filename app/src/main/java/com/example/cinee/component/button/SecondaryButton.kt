package com.example.cinee.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cinee.component.text.BodyText
import com.example.cinee.ui.theme.CineeTheme
import com.example.cinee.ui.theme.Dimens

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    text:String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
)
{
    val buttonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    OutlinedButton (
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = buttonColors,
        border = BorderStroke(
            width = 1.dp,
            color = when {
                !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                else -> MaterialTheme.colorScheme.outlineVariant
            }
        )
    ) {
        BodyText(
            text = text,
            textAlign = TextAlign.Center,
            color = if(enabled) buttonColors.contentColor else buttonColors.disabledContentColor,
            modifier = Modifier.padding(Dimens.paddingSmall)
        )
    }
}


// Preview
@Preview(name = "Secondary Button", showBackground = true)
@PreviewLightDark
@Composable
fun SecondaryButtonPreview() {
    CineeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton(
                onClick = {},
                text = "Enabled",
            )
            SecondaryButton(
                onClick = {},
                text = "Disabled",
                enabled = false
            )
        }
    }
}