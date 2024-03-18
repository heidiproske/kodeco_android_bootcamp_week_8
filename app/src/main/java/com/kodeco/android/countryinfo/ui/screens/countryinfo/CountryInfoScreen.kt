package com.kodeco.android.countryinfo.ui.screens.countryinfo

import android.os.Parcelable
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.sample.MockCountryRepository
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.ErrorRetryAlertDialog
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CountryInfoState : Parcelable {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel,
) {
    val state: CountryInfoState by viewModel.uiState.collectAsState()

    Surface {
        when (val curState = state) {
            is CountryInfoState.Loading -> Loading()

            is CountryInfoState.Success -> CountryInfoList(curState.countries) {
                viewModel.refresh()
            }

            is CountryInfoState.Error -> {
                ErrorRetryAlertDialog(
                    message = "An error occurred: ${curState.error.message}",
                    onRetry = { viewModel.refresh() }
                )
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoScreenPreview() {
    CountryInfoScreen(viewModel = CountryInfoViewModel(MockCountryRepository()))
}
