package com.helptrickbd.class1.core.designsystem.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.theme.AppTheme

/**
 * World-Class Exit Confirmation Dialog.
 * Protects users from accidental app termination at the navigation root.
 */
@Composable
fun ExitConfirmationDialog(
    onConfirmExit: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(
                text = stringResource(R.string.exit_dialog_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        },
        text = {
            Text(
                text = stringResource(R.string.exit_dialog_message),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmExit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.exit_dialog_confirm),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.exit_dialog_cancel),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        modifier = modifier
    )
}

@Preview(name = "Exit Dialog - Light")
@Composable
private fun ExitConfirmationDialogPreview() {
    AppTheme(darkTheme = false) {
        ExitConfirmationDialog(
            onConfirmExit = {},
            onDismiss = {}
        )
    }
}

@Preview(name = "Exit Dialog - Dark")
@Composable
private fun ExitConfirmationDialogDarkPreview() {
    AppTheme(darkTheme = true) {
        ExitConfirmationDialog(
            onConfirmExit = {},
            onDismiss = {}
        )
    }
}
