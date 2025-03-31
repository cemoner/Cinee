package com.example.cinee.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
fun PrimaryButton(
    onClick: () -> Unit,
    text:String,
    modifier:Modifier = Modifier,
    enabled: Boolean = true
){
    val buttonColors = ButtonDefaults.filledTonalButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        colors = buttonColors,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
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
@Preview(name = "Primary Button", showBackground = true)
@PreviewLightDark
@Composable
fun PrimaryButtonPreview() {
    CineeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(
                onClick = {},
                text = "Enabled",
            )
            PrimaryButton(
                onClick = {},
                text = "Disabled",
                enabled = false
            )
        }
    }
}