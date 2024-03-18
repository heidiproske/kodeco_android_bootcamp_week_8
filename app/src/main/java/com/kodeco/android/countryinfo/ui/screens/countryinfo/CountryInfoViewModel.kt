package com.kodeco.android.countryinfo.ui.screens.countryinfo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountryInfoViewModel {
    // Private mutable state flow for internal use
    private val _uiState = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)

    // Publicly exposed immutable state flow
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()
}