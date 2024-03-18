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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.sample.sampleCountries
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen

@Composable
fun CountryInfoList(
    countries: List<Country>,
    onRefreshClick: () -> Unit,
) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    val tapCounter = remember { mutableStateOf(0) }
    val backCounter = remember { mutableStateOf(0) }

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
                backCounter.value++
            }
        } ?: run {
            LazyColumn {
                items(countries) { country ->
                    CountryInfoRow(country) {
                        selectedCountry = country
                        tapCounter.value++
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
