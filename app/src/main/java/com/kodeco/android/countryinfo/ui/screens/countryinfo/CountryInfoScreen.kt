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
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.sample.sampleCountries
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.parcelize.Parcelize
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

@Parcelize
sealed class CountryInfoState : Parcelable {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(
    service: CountryService,
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
            getCountryInfoFlow(service)
                .catch {
                    state = CountryInfoState.Error(it)
                }
                .collect { countryInfoState ->
                    state = countryInfoState
                }
        }
    }
}

// TODO: Remove this method and split it out into a viewmodel and repository.
//  The repo should be responsible for fetching the list of Country objects from the API service.
//  The viewmodel should be responsible for converting the response from the repo to a CountryInfoState object.
private fun getCountryInfoFlow(service: CountryService): Flow<CountryInfoState> = flow {
    delay(1_000) // Added for displaying the uptime longer on the loading screen.
    val countriesResponse = service.getAllCountries()

    val newState = if (countriesResponse.isSuccessful) {
        CountryInfoState.Success(countriesResponse.body()!!)
    } else {
        CountryInfoState.Error(Throwable("Request failed: ${countriesResponse.message()}"))
    }

    emit(newState)
}

@Preview
@Composable
fun CountryInfoScreenPreview() {
    CountryInfoScreen(
        service = object : CountryService {
            override suspend fun getAllCountries(): Response<List<Country>> =
                Response.success(sampleCountries)
        },
    )
}
