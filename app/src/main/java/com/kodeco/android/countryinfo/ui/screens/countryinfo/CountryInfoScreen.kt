package com.kodeco.android.countryinfo.ui.screens.countryinfo

import android.os.Parcelable
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.R
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
    val counter by viewModel.counterFlow.collectAsState()

    Surface {
        when (val curState = state) {
            is CountryInfoState.Loading -> Loading(counter)

            is CountryInfoState.Success -> CountryInfoList(curState.countries) {
                viewModel.refresh()
            }

            is CountryInfoState.Error -> {
                val errorMessage = curState.error.message ?: ""
                ErrorRetryAlertDialog(
                    message = stringResource(
                        R.string.error_message_failed_to_fetch_countries,
                        errorMessage
                    ),
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
