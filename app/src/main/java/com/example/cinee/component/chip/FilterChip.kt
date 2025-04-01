package com.example.cinee.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.cinee.ui.theme.CineeTheme

@Composable
fun CustomFilterChip(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        label = { 
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge
            )
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Selected",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        } else null,
        shape = MaterialTheme.shapes.extraSmall,
        border = FilterChipDefaults.filterChipBorder(
            enabled = enabled,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outline
        ),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    )
}

@Preview(name = "Filter Chip", showBackground = true)
@PreviewLightDark
@Composable
fun FilterChipPreview() {
    CineeTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            CustomFilterChip(
                selected = true,
                onSelectedChange = { },
                label = "Selected"
            )
            CustomFilterChip(
                selected = false,
                onSelectedChange = { },
                label = "Unselected"
            )
            CustomFilterChip(
                selected = false,
                onSelectedChange = { },
                label = "Disabled",
                enabled = false
            )
        }
    }
}

