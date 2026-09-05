package com.helptrickbd.class1.feature.learn_hub.ui.slate.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.helptrickbd.class1.R

@Composable
fun SaveSuccessDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = Color(0xFF10B981))
        },
        title = {
            Text(stringResource(R.string.slate_save_art))
        },
        text = {
            Text(stringResource(R.string.slate_save_success))
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(android.R.string.ok))
            }
        }
    )
}
