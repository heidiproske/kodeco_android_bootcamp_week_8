package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Display an error message and a retry button that calls the `onRetry` callback.
@Composable
fun ErrorRetryAlertDialog(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Don't allow user to dismiss the dialog. There's only one path - to "Retry" */ },
        title = { Text("Error") },
        text = { Text(message) },
        confirmButton = {
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                Text("Retry")
            }
        },
        modifier = modifier,
    )
}