package com.example.cinee.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.cinee.ui.theme.CineeTheme

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    text: String,
    confirmText: String = "Confirm",
    dismissText: String = "Dismiss",
    modifier: Modifier = Modifier,
    showDialog: Boolean = true
) {
    if (showDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start
                )
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = confirmText,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = dismissText,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },  
            shape = MaterialTheme.shapes.extraLarge,
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            tonalElevation = AlertDialogDefaults.TonalElevation
        )
    }
}

@Preview(name = "Alert Dialog", showBackground = true)
@PreviewLightDark
@Composable
fun CustomAlertDialogPreview() {
    CineeTheme {
        CustomAlertDialog(
            onDismissRequest = { },
            onConfirmation = { },
            title = "Delete Account",
            text = "Are you sure you want to delete your account? This action cannot be undone.",
            confirmText = "Delete",
            dismissText = "Cancel"
        )
    }
}

