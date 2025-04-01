package com.example.cinee.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
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
fun CustomInputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null
) {
    InputChip(
        selected = selected,
        onClick = onClick,
        label = { 
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge
            )
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon?.let { 
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = "Remove",
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        },
        shape = MaterialTheme.shapes.small,
        border = InputChipDefaults.inputChipBorder(
            enabled = enabled,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outline
        ),
        colors = InputChipDefaults.inputChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    )
}

@Preview(name = "Input Chip", showBackground = true)
@PreviewLightDark
@Composable
fun InputChipPreview() {
    CineeTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            CustomInputChip(
                selected = true,
                onClick = { },
                label = "Selected",
                trailingIcon = Icons.Default.Close
            )
            CustomInputChip(
                selected = false,
                onClick = { },
                label = "Unselected",
                trailingIcon = Icons.Default.Close
            )
            CustomInputChip(
                selected = false,
                onClick = { },
                label = "Disabled",
                enabled = false,
                trailingIcon = Icons.Default.Close
            )
            CustomInputChip(
                selected = false,
                onClick = { },
                label = "No Icon"
            )
        }
    }
} 