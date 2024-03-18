package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.flow.Flows
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.sample.sampleCountries

@Composable
fun CountryInfoList(
    countries: List<Country>,
    onRefreshClick: () -> Unit,
) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    // TODO: Replace these with standard `remember`ed objects that utilize `mutableStateOf()`
    val tapCounter = Flows.tapFlow.collectAsState()
    val backCounter = Flows.backFlow.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Taps: ${tapCounter.value}",
                textAlign = TextAlign.Start,
            )
            Button(
                onClick = onRefreshClick,
            ) {
                Text(text = "Refresh")
            }
            Text(
                text = "Back: ${backCounter.value}",
                textAlign = TextAlign.End,
            )
        }

        selectedCountry?.let { country ->
            CountryDetailsScreen(country) {
                selectedCountry = null
                // TODO: Make sure to increment backCounter.value
            }
        } ?: run {
            LazyColumn {
                items(countries) { country ->
                    CountryInfoRow(country) {
                        selectedCountry = country
                        // TODO: Replace with an increment of tapCounter.value
                        Flows.tap()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoListPreview() {
    CountryInfoList(
        countries = sampleCountries,
        onRefreshClick = {},
    )
}
