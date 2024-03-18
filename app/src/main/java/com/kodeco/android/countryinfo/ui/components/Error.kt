package com.kodeco.android.countryinfo.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Error(error: Throwable, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Log.e("CountryErrorScreen", "Error: ${error.message}")
        Text(text = "Error: ${error.message}")
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Preview
@Composable
fun ErrorPreview() {
    Error(
        error = Throwable("Error message"),
        onRetry = {},
    )
}
