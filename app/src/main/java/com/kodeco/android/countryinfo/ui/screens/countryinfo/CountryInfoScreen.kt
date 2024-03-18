package com.kodeco.android.countryinfo.ui.screens.countryinfo

import android.os.Parcelable
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.sample.MockCountryRepository
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.parcelize.Parcelize
import repositories.CountryRepository

@Parcelize
sealed class CountryInfoState : Parcelable {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(
    repository: CountryRepository,
) {
    var state: CountryInfoState by rememberSaveable { mutableStateOf(CountryInfoState.Loading) }

    Surface {
        when(val curState = state) {
            is CountryInfoState.Loading -> Loading()
            is CountryInfoState.Success -> CountryInfoList(curState.countries) {
                state = CountryInfoState.Loading
            }
            is CountryInfoState.Error -> Error(curState.error) {
                state = CountryInfoState.Loading
            }
        }
    }

    // TODO: Move this logic in to the viewmodel.
    if (state == CountryInfoState.Loading) {
        LaunchedEffect(key1 = "fetch-countries") {
            // TODO: The viewmodel should be responsible for converting the response from the repo to a CountryInfoState object.
            delay(1_000) // Added for displaying the uptime longer on the loading screen.

            repository.fetchCountries()
                .catch { e ->
                    state = CountryInfoState.Error(e)
                }
                .collect { countries ->
                    state = CountryInfoState.Success(countries)
                }
        }
    }
}

@Preview
@Composable
fun CountryInfoScreenPreview() {
    CountryInfoScreen(repository = MockCountryRepository())
}
