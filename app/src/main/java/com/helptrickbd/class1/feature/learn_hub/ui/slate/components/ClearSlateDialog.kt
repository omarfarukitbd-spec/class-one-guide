package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.helptrickbd.class1.R

@Composable
fun ClearSlateDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(Icons.Rounded.DeleteSweep, contentDescription = null, tint = MaterialTheme.colorScheme.error)
        },
        title = {
            Text(stringResource(R.string.slate_clear_dialog_title))
        },
        text = {
            Text(stringResource(R.string.slate_clear_dialog_msg))
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.slate_clear_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.slate_clear_dialog_cancel))
            }
        }
    )
}
