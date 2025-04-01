package com.example.cinee.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cinee.ui.theme.CineeTheme

@Composable
fun ActionChip(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    AssistChip(
        onClick = onClick,
        label = { 
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge
            )
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = icon?.let { 
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        shape = MaterialTheme.shapes.small,
        border = AssistChipDefaults.assistChipBorder(
            enabled = enabled,
            borderColor = MaterialTheme.colorScheme.outline
        ),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            leadingIconContentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledLeadingIconContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    )
}

@Preview(name = "Action Chip", showBackground = true)
@PreviewLightDark
@Composable
fun ActionChipPreview() {
    CineeTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            ActionChip(
                onClick = { },
                label = "Add to List",
                icon = Icons.Default.Add
            )
            ActionChip(
                onClick = { },
                label = "Disabled",
                icon = Icons.Default.Add,
                enabled = false
            )
            ActionChip(
                onClick = { },
                label = "No Icon"
            )
        }
    }
}

