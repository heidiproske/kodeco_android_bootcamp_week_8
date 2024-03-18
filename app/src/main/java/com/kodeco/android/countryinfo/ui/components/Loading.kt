package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.flow.Flows

@Composable
fun Loading() {
    // TODO: Remove the reference to Flows here and simply pass in a `counter` value as
    //  an Int in to this Loading composable function.
    val counter = Flows.counterFlow.collectAsState(initial = 0)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(text = "Loading... App uptime: ${counter.value}")
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingPreview() {
    Loading()
}
